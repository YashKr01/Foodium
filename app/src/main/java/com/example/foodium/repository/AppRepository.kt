package com.example.foodium.repository

import androidx.lifecycle.MutableLiveData
import com.example.foodium.data.network.ApiInterface
import com.example.foodium.data.network.model.FoodJoke
import com.example.foodium.data.network.model.Result
import com.example.foodium.utils.Resource
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiInterface: ApiInterface) {

    private val _recipesList = MutableLiveData<Resource<List<Result>>>()
    val recipesList get() = _recipesList

    private val _searchedRecipesList = MutableLiveData<Resource<List<Result>>>()
    val searchedRecipesList get() = _searchedRecipesList

    private val _foodJoke = MutableLiveData<Resource<FoodJoke>>()
    val foodJoke get() = _foodJoke

    suspend fun getRecipesList() {
        _recipesList.postValue(Resource.Loading(null))
        try {
            val response = apiInterface.getRecipes().results
            _recipesList.postValue(Resource.Success(response))
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