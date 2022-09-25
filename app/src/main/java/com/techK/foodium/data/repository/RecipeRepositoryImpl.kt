package com.techK.foodium.data.repository

import com.techK.foodium.data.databse.dao.RecipeDao
import com.techK.foodium.data.datastore.RecipesDataStore
import com.techK.foodium.data.network.RecipeApi
import com.techK.foodium.data.response.RecipesResponse
import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.enums.SortOrder
import com.techK.foodium.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApi,
    private val dao: RecipeDao,
    private val datastore: RecipesDataStore,
) : RecipeRepository {

    override suspend fun getRefreshQuery(): Flow<Boolean> = flow {
        datastore.getRefreshQuery().collect { emit(it) }
    }

    override val sortOrder: Flow<SortOrder>
        get() = datastore.sortOrder

    override suspend fun getRecipes(): RecipesResponse {
        return api.getRecipes()
    }

    override suspend fun getSavedRecipes(): Flow<List<Recipe>> {
        return dao.getSavedRecipes()
    }

    override suspend fun saveRecipe(recipe: Recipe) {
        dao.saveRecipe(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        dao.deleteRecipe(recipe)
    }

    override suspend fun setRefresh(refresh: Boolean) {
        datastore.setRefresh(refresh)
    }

    override suspend fun setSortOrder(sortOrder: SortOrder) {
        datastore.setSortOrder(sortOrder)
    }

    override suspend fun deleteAllRecipes() {
        dao.deleteAllRecipes()
    }

    override suspend fun getRecipesByOrder(sortOrder: SortOrder): Flow<List<Recipe>> {
        return dao.getSavedRecipesByOrder(sortOrder)
    }

}