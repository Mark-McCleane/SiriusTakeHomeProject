package com.recipes.app_compose.ui.page.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailsScreen(
    onBackPressed: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    // TODO #4: Implement the Details screen as seen in [screenshots/compose_details.png]
}

private object DetailsScreenDefaults {
    val VerticalPadding = 16.dp
    val HorizontalPadding = 16.dp
    val IngredientVerticalPadding = 2.dp
    val IngredientsHeaderBottomPadding = 8.dp
    val ImageHeight = 250.dp
}
