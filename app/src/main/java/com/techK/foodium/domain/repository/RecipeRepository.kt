package com.techK.foodium.domain.repository

import com.techK.foodium.data.response.RecipesResponse
import com.techK.foodium.domain.entities.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRecipes(): RecipesResponse

    suspend fun getSavedRecipes(): Flow<List<Recipe>>

}