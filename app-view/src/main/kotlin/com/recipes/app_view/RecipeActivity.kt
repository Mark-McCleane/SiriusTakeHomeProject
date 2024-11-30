package com.recipes.app_view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import androidx.navigation.ui.setupWithNavController
import com.recipes.app_view.databinding.ActivityRecipeBinding
import com.recipes.app_view.ui.detail.RecipeDetailFragment
import com.recipes.app_view.ui.recipesearch.RecipeSearchFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@Serializable
data object Search

@Serializable
data class RecipeId(val id: String, val title: String)

@AndroidEntryPoint
class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        navController.graph = navController.createGraph(
            startDestination = Search
        ) {
            fragment<RecipeSearchFragment, Search> {
                label = "Recipes"
            }

            fragment<RecipeDetailFragment, RecipeId> {
                argument("title") { type = NavType.StringType }
                label = "{title}"
            }
        }

        binding.myToolbar.setupWithNavController(navController)
    }
}

