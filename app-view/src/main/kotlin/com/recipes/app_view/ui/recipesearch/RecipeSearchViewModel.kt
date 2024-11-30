package com.recipes.app_view.ui.recipesearch

import androidx.lifecycle.ViewModel
import com.recipes.recipesdk.models.Recipe
import com.recipes.recipesdk.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeSearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {

    // TODO #1:  In RecipeSearchViewModel.kt: Return a stateflow of search results using the
    //  searchRepository. The screen should show a blank page when the query is empty, a loading
    //  widget while the page is loading, and a list of results when the repository returns data.
    //  See the screenshots in /screenshots.

    data class State(
        val isLoading: Boolean = false,
        val errorMessage: String = "",
        val recipes: List<Recipe> = emptyList(),
    ) {
        companion object {
            val DEFAULT_STATE: State = State()
        }
    }
}
