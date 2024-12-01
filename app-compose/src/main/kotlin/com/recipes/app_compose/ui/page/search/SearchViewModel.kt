package com.recipes.app_compose.ui.page.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipes.recipesdk.models.Recipe
import com.recipes.recipesdk.models.Result
import com.recipes.recipesdk.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorFlow = MutableStateFlow<String?>(null)
    val errorFlow: StateFlow<String?> = _errorFlow.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Recipe>>(emptyList())
    val searchResults: StateFlow<List<Recipe>> = searchQuery
        .debounce(1_000L)
        .onEach { _isLoading.update { true } }
        .combine(_searchResults) { text, recipe ->
            recipe
        }.onEach { _isLoading.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _searchResults.value
        )

    init {
        searchRecipe()
    }

    fun onSearchTextChange(text: String) {
        _searchQuery.value = text
        searchRecipe()
    }

    private fun searchRecipe() {
        viewModelScope.launch {
            _isLoading.update { true }
            searchQuery
                .debounce(300L)
                .collect { query ->
                    if (query.isBlank()) {
                        _searchResults.update { emptyList() }
                    } else {
                        repository.searchRecipe(query).collect { result ->
                            if (result is Result.Success) {
                                _searchResults.update { result.data }
                            } else if (result is Result.Error) {
                                _errorFlow.update { result.message }
                            } else if (result is Result.Loading) {
                                _isLoading.update { true }
                            }
                        }
                    }
                }
            _isLoading.update { false }
        }
    }

    fun emptySearchText() {
        _searchQuery.update { "" }
    }

    fun removeError() {
        _errorFlow.update { null }
    }
}
