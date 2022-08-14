package com.example.foodium.data.network

import com.example.foodium.data.network.model.FoodJoke
import com.example.foodium.data.network.model.RecipesResponse
import com.example.foodium.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET("/recipes/complexSearch?addRecipeInformation=true&fillIngredients=true&number=50")
    suspend fun getRecipes(@Query("apiKey") apiKey: String = Constants.API_KEY.random()): RecipesResponse

    @GET("/recipes/complexSearch?addRecipeInformation=true&fillIngredients=true")
    suspend fun searchRecipes(@QueryMap searchQuery: Map<String, String>): RecipesResponse

    @GET("food/jokes/random?apiKey=")
    suspend fun getFoodJoke(): FoodJoke

}