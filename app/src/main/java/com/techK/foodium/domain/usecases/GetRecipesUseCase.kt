package com.techK.foodium.domain.usecases

import com.techK.foodium.data.network.RecipeApi
import com.techK.foodium.data.response.toRecipe
import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val api: RecipeApi,
) {

    operator fun invoke(): Flow<Resource<List<Recipe>>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.getRecipes()
            val recipeList = response.results.map {
                it.toRecipe()
            }
            emit(Resource.Success(recipeList))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error Occurred"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach the server"))
        }
    }

}