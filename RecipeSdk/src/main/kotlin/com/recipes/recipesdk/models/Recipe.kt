package com.recipes.recipesdk.models

import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("recipe_id")
    val recipeId: String,
    val title: String,
    val publisher: String,
    @SerializedName("publisher_url")
    val publisherUrl: String,
    @SerializedName("source_url")
    val sourceUrl: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("social_rank")
    val socialRank: Float,
    val ingredients: List<String>?,
)
