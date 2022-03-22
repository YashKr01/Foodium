package com.example.foodium.data.network.model

import com.google.gson.annotations.SerializedName

data class RecipesResponse(
    val number: Int,
    @SerializedName("results")
    val results: List<Result>,
    val totalResults: Int
)