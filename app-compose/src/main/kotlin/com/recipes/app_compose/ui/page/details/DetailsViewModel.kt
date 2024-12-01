package com.recipes.app_compose.ui.page.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.recipes.app_compose.Destination
import com.recipes.recipesdk.models.Recipe
import com.recipes.recipesdk.models.Result
import com.recipes.recipesdk.repository.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val recipeId = savedStateHandle.toRoute<Destination.Details>().id

    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe = _recipe.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorFlow = MutableStateFlow<String?>(null)
    val errorFlow = _errorFlow.asStateFlow()

    init {
        getRecipe()
    }

    private fun getRecipe() {
        viewModelScope.launch {
            _isLoading.update { true }
            repository.getRecipe(recipeId).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _recipe.update { result.data }
                        _isLoading.update { false }
                    }

                    is Result.Error -> {
                        _errorFlow.update { result.message }
                        _isLoading.update { false }
                    }

                    is Result.Loading -> {
//                        delay(1000L) // simulate network delay
                        _isLoading.update { true }
                    }
                }
            }
        }
    }

    fun removeError() {
        _errorFlow.update { null }
    }
}
