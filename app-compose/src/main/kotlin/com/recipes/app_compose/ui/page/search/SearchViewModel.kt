package com.recipes.app_compose.ui.page.search

import androidx.lifecycle.ViewModel
import com.recipes.recipesdk.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
) : ViewModel() {
    // TODO #1: Implement the search screen's ViewModel. The screen should show a blank
    //  page when the query is empty, a loading widget while the page is loading, and a list of
    //  results when the repository returns data. See the screenshots in /screenshots.
}
