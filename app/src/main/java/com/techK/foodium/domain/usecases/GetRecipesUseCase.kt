package com.techK.foodium.domain.usecases

import com.techK.foodium.data.network.RecipeApi
import com.techK.foodium.data.response.RecipesResponse
import com.techK.foodium.data.response.Result
import com.techK.foodium.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val api: RecipeApi
) {

    operator fun invoke(): Flow<Resource<List<Result>>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.getRecipes()
            emit(Resource.Success(response.results))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error Occurred"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach the server"))
        }
    }

}