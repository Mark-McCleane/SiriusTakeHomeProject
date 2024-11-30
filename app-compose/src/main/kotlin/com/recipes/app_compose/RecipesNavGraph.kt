package com.recipes.app_compose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.recipes.app_compose.ui.page.details.DetailsScreen
import com.recipes.app_compose.ui.page.search.SearchScreen
import kotlinx.serialization.Serializable

@Composable
fun RecipesNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destination.Search) {
        composable<Destination.Search> {
            SearchScreen(
                onNavigateToDetails = { itemId ->
                    navController.navigate(Destination.Details(itemId))
                }
            )
        }

        composable<Destination.Details> { backStackEntry ->
            val itemId = (backStackEntry.toRoute<Destination.Details>() as? Destination.Details)?.id
            itemId?.let {
                DetailsScreen(
                    onBackPressed = { navController.popBackStack() }
                )
            }
        }
    }
}

sealed class Destination {
    @Serializable
    data object Search

    @Serializable
    data class Details(val id: String)
}
