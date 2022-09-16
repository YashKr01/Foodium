package com.techK.foodium.data.repository

import com.techK.foodium.data.network.RecipeApi
import com.techK.foodium.data.response.RecipesResponse
import com.techK.foodium.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApi,
) : RecipeRepository {

    override suspend fun getRecipes(): Flow<RecipesResponse> = flow {
        api.getRecipes()
    }

}