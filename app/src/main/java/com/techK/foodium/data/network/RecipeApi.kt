package com.techK.foodium.data.network

import com.techK.foodium.data.response.RecipesResponse
import com.techK.foodium.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RecipeApi {

    @GET("recipes/complexSearch?addRecipeInformation=true&fillIngredients=true&number=10")
    suspend fun getRecipes(@Query("apiKey") apiKey: String = Constants.API_KEY.random()): RecipesResponse

    @GET("/recipes/complexSearch?addRecipeInformation=true&fillIngredients=true")
    suspend fun searchRecipes(@QueryMap searchQuery: Map<String, String>): RecipesResponse

}