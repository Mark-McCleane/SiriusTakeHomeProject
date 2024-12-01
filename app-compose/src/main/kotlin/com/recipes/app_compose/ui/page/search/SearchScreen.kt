package com.recipes.app_compose.ui.page.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.recipes.app_compose.ui.component.SearchResultItem
import com.recipes.app_compose.ui.component.SearchResultItemUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateToDetails: (itemId: String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val searchText by viewModel.searchQuery.collectAsState()
    val isSearching by viewModel.isLoading.collectAsState()
    val recipeList by viewModel.searchResults.collectAsState()
    val error by viewModel.errorFlow.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = error) {
        if (!error.isNullOrBlank()) {
            snackbarHostState.showSnackbar(
                message = error!!,
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite
            )
        }
        viewModel.removeError()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Recipes") })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TextField(
                value = searchText,
                onValueChange = viewModel::onSearchTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                placeholder = { Text(text = "Search") },
                shape = MaterialTheme.shapes.large,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                leadingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        viewModel.emptySearchText()
                        keyboardController?.hide()
                    }) {
                        if (searchText.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    }
                }
            )

            if (isSearching) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            } else {
                if (searchText.isNotEmpty() && recipeList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "No Results For \"$searchText\"!",
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ) {
                        items(
                            items = recipeList,
                            key = { it.recipeId }
                        ) {
                            SearchResultItem(
                                SearchResultItemUiState(
                                    title = it.title,
                                    subtitle = it.publisher,
                                    imageUrl = it.imageUrl,
                                    id = it.recipeId
                                ),
                                onClick = { onNavigateToDetails(it.recipeId) },
                            )
                            HorizontalDivider()
                        }
                    }
                    keyboardController?.hide()
                }
            }
        }
    }
}

