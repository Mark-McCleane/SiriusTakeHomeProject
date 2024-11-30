package com.recipes.app_compose.ui.page.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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

    Scaffold(topBar = {
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
    }) { paddingValues ->
        TextField(
            value = searchText,
            onValueChange = viewModel::onSearchTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search Tv Show") },
            trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Tv Shows"
                    )
                }
            }
        )
    }

}

private object SearchScreenDefaults {
    val VerticalPadding = 16.dp
    val HorizontalPadding = 24.dp
    val ErrorTopPadding = 64.dp
}
