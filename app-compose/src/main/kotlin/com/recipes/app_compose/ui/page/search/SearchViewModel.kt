package com.recipes.app_compose.ui.page.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipes.recipesdk.models.Recipe
import com.recipes.recipesdk.models.Result
import com.recipes.recipesdk.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
) : ViewModel() {
    // TODO #1: Implement the search screen's ViewModel. The screen should show a blank
    //  page when the query is empty, a loading widget while the page is loading, and a list of
    //  results when the repository returns data. See the screenshots in /screenshots.#

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Recipe>>(emptyList())
    val searchResults: StateFlow<List<Recipe>> = _searchResults.asStateFlow()

    private val _errorFlow = MutableStateFlow<String?>(null)
    val errorFlow: StateFlow<String?> = _errorFlow.asStateFlow()

    init {
        searchRecipe()
    }

    private fun searchRecipe(inputText: String = "") {
        viewModelScope.launch {
            searchQuery.collect { query ->
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
                _isLoading.update { false }
            }
        }
    }
}
