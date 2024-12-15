package com.recipes.app_compose.ui.page.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.recipes.app_compose.R
import com.recipes.app_compose.ui.component.Image
import com.recipes.app_compose.ui.page.details.DetailsScreenDefaults.HorizontalPadding
import com.recipes.app_compose.ui.page.details.DetailsScreenDefaults.IngredientVerticalPadding
import com.recipes.app_compose.ui.page.details.DetailsScreenDefaults.IngredientsHeaderBottomPadding
import com.recipes.app_compose.ui.page.details.DetailsScreenDefaults.VerticalPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    onBackPressed: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    val recipe by viewModel.recipe.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.errorFlow.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect (key1 = error){
        if (!error.isNullOrBlank()) {
            snackbarHostState.showSnackbar(
                message = error!!,
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite
            )
        }
        viewModel.removeError()
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(recipe?.title ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                        )
                },
                    navigationIcon = {
                        IconButton(onClick = onBackPressed) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back Arrow"
                            )
                        }
                    })

            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
            ) { innerPadding ->
            Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                Image(
                    imageUrl = recipe?.imageUrl ?: "",
                    contentDescription = "Recipe Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(DetailsScreenDefaults.ImageHeight),
                    contentScale = ContentScale.FillBounds
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = HorizontalPadding, vertical = VerticalPadding)
                ) {
                    Text(
                        text = recipe?.title ?: "",
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = recipe?.socialRank.toString(),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(0.2f),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = IngredientsHeaderBottomPadding,
                            top = VerticalPadding,
                            start = HorizontalPadding,
                            end = HorizontalPadding
                        )
                ) {
                    Text(
                        text = stringResource(R.string.ingredients),
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                if (recipe != null && recipe!!.ingredients != null) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(
                                horizontal = HorizontalPadding,
                                vertical = IngredientVerticalPadding
                            )
                    ) {
                        items(
                            items = recipe!!.ingredients!!
                        ) {
                            Text(it)
                        }
                    }
                }
            }
        }
    }
}

private object DetailsScreenDefaults {
    val VerticalPadding = 16.dp
    val HorizontalPadding = 16.dp
    val IngredientVerticalPadding = 2.dp
    val IngredientsHeaderBottomPadding = 8.dp
    val ImageHeight = 250.dp
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    DetailsScreen(onBackPressed = {})
}
