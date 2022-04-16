package com.example.foodium.repository

import com.example.foodium.data.database.RecipeDao
import com.example.foodium.data.database.model.RecipeEntity
import com.example.foodium.data.network.ApiInterface
import com.example.foodium.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val dao: RecipeDao
) {

    private val _recipesList =
        MutableStateFlow<Resource<List<RecipeEntity>>>(Resource.Loading(null))
    val recipeList get() = _recipesList

    suspend fun getRecipesList() {
        _recipesList.emit(Resource.Loading(null))
        try {
            val response = apiInterface.getRecipes().results
            val databaseList = dao.getRecipesList().first()
            val mappedList = response.map { recipe ->

                val isSaved = databaseList.any {
                    it.recipeId == recipe.recipeId
                }

                RecipeEntity(
                    instructions = recipe.instructions,
                    aggregateLikes = recipe.aggregateLikes,
                    cheap = recipe.cheap,
                    dairyFree = recipe.dairyFree,
                    extendedIngredients = recipe.extendedIngredients,
                    glutenFree = recipe.glutenFree,
                    recipeId = recipe.recipeId,
                    image = recipe.image,
                    readyInMinutes = recipe.readyInMinutes,
                    sourceName = recipe.sourceName,
                    sourceUrl = recipe.sourceUrl,
                    summary = recipe.summary,
                    title = recipe.title,
                    vegan = recipe.vegan,
                    vegetarian = recipe.vegetarian,
                    veryHealthy = recipe.veryHealthy,
                    popular = recipe.popular,
                    saved = isSaved
                )

            }
            _recipesList.emit(Resource.Success(mappedList))
        } catch (e: IOException) {
            _recipesList.emit(Resource.Error("No Connection", null))
        } catch (e: Exception) {
            _recipesList.emit(Resource.Error(e.message.toString(), null))
        }
    }

    suspend fun insertRecipe(recipeEntity: RecipeEntity) = dao.insertRecipe(recipeEntity)

    suspend fun deleteRecipe(recipeEntity: RecipeEntity) = dao.deleteRecipe(recipeEntity)

    fun getSavedRecipesList() = dao.getRecipesList()

}