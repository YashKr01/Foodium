package com.techK.foodium.domain.repository

import com.techK.foodium.data.response.RecipesResponse
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRecipes(): Flow<RecipesResponse>

}