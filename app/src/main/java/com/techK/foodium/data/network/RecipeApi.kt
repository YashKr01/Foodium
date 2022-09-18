package com.techK.foodium.data.network

import com.techK.foodium.data.response.RecipesResponse
import com.techK.foodium.domain.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("recipes/complexSearch?addRecipeInformation=true&fillIngredients=true&number=10")
    suspend fun getRecipes(@Query("apiKey") apiKey: String = "daf834a922f44a40bbebbcba81847580"): RecipesResponse

}