package com.example.foodium.data.network

import com.example.foodium.data.network.model.FoodJoke
import com.example.foodium.data.network.model.RecipesResponse
import com.example.foodium.utils.Constants.API_KEY1
import com.example.foodium.utils.Constants.API_KEY2
import com.example.foodium.utils.Constants.API_KEY3
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET("/recipes/complexSearch?apiKey=$API_KEY1")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<RecipesResponse>

    @GET("/recipes/complexSearch?apiKey=$API_KEY2")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String, String>
    ): Response<RecipesResponse>

    @GET("food/jokes/random?apiKey=$API_KEY3")
    suspend fun getFoodJoke(
        @Query("apiKey") apiKey: String
    ): Response<FoodJoke>

}