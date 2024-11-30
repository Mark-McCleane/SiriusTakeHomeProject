package com.recipes.app_compose.ui.page.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.recipes.app_compose.ui.page.search.SearchScreenDefaults.HorizontalPadding
import com.recipes.app_compose.ui.page.search.SearchScreenDefaults.VerticalPadding

@Composable
fun SearchScreen(
    onNavigateToDetails: (itemId: String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    // TODO #2: Display a search bar and a list of results.
    //  See [screenshots/compose_search_results.png]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Text(
            text = "Recipes",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(
                horizontal = HorizontalPadding,
                vertical = VerticalPadding
            )
        )
    }
}

private object SearchScreenDefaults {
    val VerticalPadding = 16.dp
    val HorizontalPadding = 24.dp
    val ErrorTopPadding = 64.dp
}
