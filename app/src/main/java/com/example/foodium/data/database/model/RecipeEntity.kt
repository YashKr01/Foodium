package com.example.foodium.data.database.model

import com.example.foodium.data.network.model.AnalyzedInstruction
import com.example.foodium.data.network.model.ExtendedIngredient

data class RecipeEntity(
    val instructions: List<AnalyzedInstruction>?,
    val aggregateLikes: Int,
    val cheap: Boolean,
    val dairyFree: Boolean,
    val extendedIngredients: List<ExtendedIngredient>?,
    val glutenFree: Boolean,
    val recipeId: Int,
    val image: String,
    val readyInMinutes: Int,
    val sourceName: String?,
    val sourceUrl: String,
    val summary: String,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val popular: Boolean,
    val saved: Boolean
)
