package com.example.foodium.repository

import com.example.foodium.data.database.RecipeDao
import com.example.foodium.data.database.model.RecipeEntity
import com.example.foodium.data.datastore.PreferenceStorage
import com.example.foodium.data.network.ApiInterface
import com.example.foodium.utils.Resource
import com.example.foodium.utils.SortOrder
import kotlinx.coroutines.flow.*
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val dao: RecipeDao,
    private val preferenceStorage: PreferenceStorage
) {

    private val _recipesList =
        MutableStateFlow<Resource<List<RecipeEntity>>>(Resource.Loading(null))
    val recipeList get() = _recipesList

    private val _refreshList = preferenceStorage.refreshList
    val refreshList get() = _refreshList

    private val _sortOrder = preferenceStorage.sortOrder
    val sortOrder get() = _sortOrder

    // NETWORK OPERATIONS
    suspend fun getRecipesList(query: HashMap<String, String>?) {
        _recipesList.emit(Resource.Loading(null))
        try {

            val response = query?.let { apiInterface.searchRecipes(it).results }
                ?: apiInterface.getRecipes().results

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
                    healthScore = recipe.healthScore,
                    saved = isSaved
                )

            }
            _recipesList.emit(Resource.Success(mappedList.shuffled()))
        } catch (e: IOException) {
            _recipesList.emit(Resource.Error("No Connection", null))
        } catch (e: Exception) {
            _recipesList.emit(Resource.Error(e.message.toString(), null))
        }
    }

    // DATABASE OPERATIONS
    suspend fun insertRecipe(recipeEntity: RecipeEntity) = dao.insertRecipe(recipeEntity)

    suspend fun deleteRecipe(recipeEntity: RecipeEntity) = dao.deleteRecipe(recipeEntity)

    fun getSavedRecipesList() = dao.getRecipesList()

    fun getSavedRecipesListByOrder(sortOrder: SortOrder) = dao.getRecipesListByOrder(sortOrder)

    suspend fun setRefreshQuery(refreshList: Boolean) = preferenceStorage.setRefresh(refreshList)

    suspend fun deleteAllRecipes() = dao.deleteAllRecipes()

    suspend fun setSortOrder(sortOrder: SortOrder) = preferenceStorage.setSortOrder(sortOrder)

}