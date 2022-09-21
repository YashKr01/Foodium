package com.techK.foodium.domain.usecases

import com.techK.foodium.data.response.toRecipe
import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.repository.RecipeRepository
import com.techK.foodium.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val api: RecipeRepository,
) {

    suspend operator fun invoke(): Flow<Resource<List<Recipe>>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.getRecipes()
            val savedList = api.getSavedRecipes().first()
            val recipeList = response.results.map { recipe ->
                val isSaved = savedList.any {
                    it.id == recipe.id
                }
                if (!isSaved) recipe.toRecipe()
                else recipe.toRecipe().copy(saved = isSaved)
            }
            emit(Resource.Success(recipeList.shuffled()))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error Occurred"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach the server"))
        }
    }

}