package com.recipes.recipesdk.repository

import android.util.Log
import com.recipes.recipesdk.models.Recipe
import com.recipes.recipesdk.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeout
import java.io.IOException
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val recipeSdk: com.recipes.recipesdk.RecipeSdk,
) {
    fun getRecipe(recipeId: String): Flow<Result<Recipe>> = flow {
        emit(Result.Loading)

        val result = try {
            val response = withTimeout(TIMEOUT) {
                recipeSdk.getRecipe(recipeId)
            }

            if (response.isSuccessful) {
                val recipe = response.body()?.recipe

                if (recipe != null) {
                    Result.Success(data = recipe)
                } else {
                    Result.Error("No Recipe Found")
                }
            } else {
                Result.Error("No Recipe Found")
            }
        } catch (e: IOException) {
            Log.w(TAG, "failed to retrieve recipe", e)
            Result.Error("Network error, please try again")
        } catch (e: TimeoutCancellationException) {
            Log.w(TAG, "failed to retrieve recipe", e)
            Result.Error("Timeout error, please try again")
        }
        emit(result)
    }
        .flowOn(Dispatchers.IO)

    companion object {
        const val TAG = "DetailsRepository"
        const val TIMEOUT = 3000L
    }
}