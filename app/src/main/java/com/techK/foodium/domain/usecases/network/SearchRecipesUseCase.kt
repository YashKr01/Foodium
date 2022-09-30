package com.techK.foodium.domain.usecases.network

import com.techK.foodium.data.response.toRecipe
import com.techK.foodium.domain.repository.RecipeRepository
import com.techK.foodium.domain.utils.Constants
import com.techK.foodium.domain.utils.Constants.QUERY_API_KEY
import com.techK.foodium.domain.utils.Constants.QUERY_TYPE
import com.techK.foodium.domain.utils.Resource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository,
) {

    suspend operator fun invoke(query: String) = flow {
        try {
            emit(Resource.Loading())
            val response = repository.searchRecipes(
                hashMapOf(
                    Pair(QUERY_TYPE, query),
                    Pair(QUERY_API_KEY, Constants.API_KEY.random())
                )
            )
            val savedList = repository.getSavedRecipes().first()
            val recipeList = response.results.map { recipe ->
                val isSaved = savedList.any { it.id == recipe.id }
                if (!isSaved) recipe.toRecipe()
                else recipe.toRecipe().copy(saved = true)
            }
            emit(Resource.Success(recipeList.shuffled()))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error Occurred"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach the server"))
        }
    }

}