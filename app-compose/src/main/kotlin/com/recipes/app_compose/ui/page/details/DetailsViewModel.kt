package com.recipes.app_compose.ui.page.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.recipes.app_compose.Destination
import com.recipes.recipesdk.repository.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    repository: DetailsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val recipeId = savedStateHandle.toRoute<Destination.Details>().id

    // TODO #3: Implement a ViewModel for the details screen, using the detailsRepository
}
