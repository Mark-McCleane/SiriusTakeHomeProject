package com.recipes.app_view.adapters

import androidx.recyclerview.widget.RecyclerView
import com.recipes.app_view.databinding.ItemRecipeListBinding
import com.recipes.recipesdk.models.Recipe

class RecipeViewHolder(
    private val itemBinding: ItemRecipeListBinding,
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(recipe: Recipe, listener: OnRecipeListener) {
        // TODO #2b: bind recipe to views.
        //  It should match the image in screenshots/view_search_results.png
    }
}
