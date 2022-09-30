package com.techK.foodium.domain.repository

import com.techK.foodium.data.response.RecipesResponse
import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.enums.SortOrder
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    // region:: DataStore
    suspend fun getRefreshQuery(): Flow<Boolean>

    suspend fun getSortOrder(): Flow<SortOrder>

    suspend fun setRefresh(refresh: Boolean)

    suspend fun setSortOrder(sortOrder: SortOrder)
    // endregion

    // region:: Database
    suspend fun getSavedRecipes(): Flow<List<Recipe>>

    suspend fun getRecipesByOrder(sortOrder: SortOrder): Flow<List<Recipe>>

    suspend fun saveRecipe(recipe: Recipe)

    suspend fun deleteRecipe(recipe: Recipe)

    suspend fun deleteAllRecipes()
    // endregion

    // region:: API Responses
    suspend fun getRecipes(): RecipesResponse

    suspend fun searchRecipes(queryMap: HashMap<String, String>): RecipesResponse

    // endregion

}