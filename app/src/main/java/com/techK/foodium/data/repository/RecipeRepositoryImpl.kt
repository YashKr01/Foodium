package com.techK.foodium.data.repository

import com.techK.foodium.data.databse.dao.RecipeDao
import com.techK.foodium.data.network.RecipeApi
import com.techK.foodium.data.response.RecipesResponse
import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApi,
    private val dao: RecipeDao,
) : RecipeRepository {

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

}