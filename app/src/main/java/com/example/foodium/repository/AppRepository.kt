package com.example.foodium.repository

import com.example.foodium.data.network.ApiInterface
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getRecipesList() = apiInterface.getRecipes()

    suspend fun searchRecipes(map : Map<String, String>) = apiInterface.searchRecipes(map)

    suspend fun getFoodJoke() = apiInterface.getFoodJoke()

}