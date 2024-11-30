package com.recipes.recipesdk

import com.recipes.recipesdk.models.RecipeResponse
import com.recipes.recipesdk.models.RecipeSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeSdk {

    // SEARCH
    @GET("api/search")
    suspend fun searchRecipe(
        @Query("q") query: String,
    ) : Response<RecipeSearchResponse>

    // GET RECIPE REQUEST
    @GET("api/get")
    suspend fun getRecipe(
        @Query("rId") recipeId: String
    ): Response<RecipeResponse>
}
