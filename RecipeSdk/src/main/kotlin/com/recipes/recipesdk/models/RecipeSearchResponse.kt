package com.recipes.recipesdk.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RecipeSearchResponse {
    @SerializedName("count")
    @Expose
    var count: Int = 0

    @SerializedName("recipes")
    @Expose
    var recipes: List<Recipe> = emptyList()

    override fun toString(): String {
        return "RecipeSearchResponse(count=$count, recipes=$recipes)"
    }
}
