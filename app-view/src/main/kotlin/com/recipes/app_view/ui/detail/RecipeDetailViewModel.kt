package com.recipes.app_view.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipes.recipesdk.models.Recipe
import com.recipes.recipesdk.repository.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository,
) : ViewModel() {

    private val _recipeId = MutableStateFlow("")

    private val recipeId: StateFlow<String> = _recipeId.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = "",
    )

    fun setRecipeId(recipeId: String) {
        _recipeId.update { recipeId }
    }

    // TODO #3: Implement a ViewModel for the details screen, using the detailsRepository

    data class State(
        val recipe: Recipe? = null,
        val isLoading: Boolean = false,
        val errorMessage: String = "",
    ) {
        companion object {
            val DEFAULT_STATE: State = State()
        }
    }
}