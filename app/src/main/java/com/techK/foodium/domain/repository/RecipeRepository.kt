package com.techK.foodium.domain.repository

import com.techK.foodium.data.response.RecipesResponse
import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.enums.SortOrder
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRefreshQuery(): Flow<Boolean>

    val sortOrder : Flow<SortOrder>

    suspend fun getRecipes(): RecipesResponse

    suspend fun getSavedRecipes(): Flow<List<Recipe>>

    suspend fun getRecipesByOrder(sortOrder: SortOrder): Flow<List<Recipe>>

    suspend fun saveRecipe(recipe: Recipe)

    suspend fun deleteRecipe(recipe: Recipe)

    suspend fun setRefresh(refresh: Boolean)

    suspend fun setSortOrder(sortOrder: SortOrder)

    suspend fun deleteAllRecipes()

}