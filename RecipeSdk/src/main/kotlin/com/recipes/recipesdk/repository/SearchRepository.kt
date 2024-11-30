package com.recipes.recipesdk.repository

import com.recipes.recipesdk.RecipeSdk
import com.recipes.recipesdk.models.Recipe
import com.recipes.recipesdk.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val recipeSdk: RecipeSdk,
) {
    @OptIn(FlowPreview::class)
    fun searchRecipe(query: String): Flow<Result<List<Recipe>>> = flow {
        emit(Result.Loading)

        try {
            val result = recipeSdk.searchRecipe(query)

            if (result.isSuccessful) {
                val recipes = result.body()?.recipes

                if (recipes?.isNotEmpty() == true) {
                    emit(Result.Success(data = recipes))
                } else {
                    emit(Result.Error(message = "No results for $query"))
                }
            } else {
                val responseCode = result.code()
                val errorMessage = when (responseCode) {
                    429 -> result.message()
                    // Client error
                    in 400..499 -> "No results for $query"

                    // Server error
                    in 500..599 -> "Sorry, we're experiencing server problems"

                    // All other error codes
                    else -> "Error, please try again later"
                }

                emit(Result.Error(message = errorMessage))
            }
        } catch (_: IOException) {
            emit(Result.Error(message = "Error, please try again later"))
        }
    }
        .debounce(300)
        .flowOn(Dispatchers.IO)
}
