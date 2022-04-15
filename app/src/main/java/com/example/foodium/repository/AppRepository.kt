package com.example.foodium.repository

import androidx.lifecycle.MutableLiveData
import com.example.foodium.data.database.model.RecipeEntity
import com.example.foodium.data.network.ApiInterface
import com.example.foodium.data.network.model.FoodJoke
import com.example.foodium.data.network.model.Result
import com.example.foodium.utils.Resource
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiInterface: ApiInterface) {

    private val _recipesList = MutableLiveData<Resource<List<RecipeEntity>>>()
    val recipesList get() = _recipesList

    private val _searchedRecipesList = MutableLiveData<Resource<List<Result>>>()
    val searchedRecipesList get() = _searchedRecipesList

    private val _foodJoke = MutableLiveData<Resource<FoodJoke>>()
    val foodJoke get() = _foodJoke

    suspend fun getRecipesList() {
        _recipesList.postValue(Resource.Loading(null))
        try {
            val response = apiInterface.getRecipes().results
            val recipesList = response.map { recipe ->

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
                    saved = true
                )

            }
            _recipesList.postValue(Resource.Success(recipesList))
        } catch (e: IOException) {
            _recipesList.postValue(Resource.Error("No Connection", null))
        } catch (e: Exception) {
            _recipesList.postValue(Resource.Error(e.message.toString(), null))
        }
    }

    suspend fun getRecipesList(map: Map<String, String>) {
        _searchedRecipesList.postValue(Resource.Loading(null))
        try {
            val response = apiInterface.searchRecipes(map).results
            _searchedRecipesList.postValue(Resource.Success(response))
        } catch (e: IOException) {
            _searchedRecipesList.postValue(Resource.Error("No Connection", null))
        } catch (e: Exception) {
            _searchedRecipesList.postValue(Resource.Error(e.message.toString(), null))
        }
    }

    suspend fun getFoodJoke() {
        _foodJoke.postValue(Resource.Loading(null))
        try {
            val response = apiInterface.getFoodJoke()
            _foodJoke.postValue(Resource.Success(response))
        } catch (e: IOException) {
            _foodJoke.postValue(Resource.Error("No Connection", null))
        } catch (e: Exception) {
            _foodJoke.postValue(Resource.Error(e.message.toString(), null))
        }
    }

}