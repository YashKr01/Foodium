package com.techK.foodium.data.response

import com.techK.foodium.domain.entities.Recipe

data class Result(
    val aggregateLikes: Int = 0,
    val analyzedInstructions: List<AnalyzedInstruction> = listOf(),
    val author: String = "",
    val cheap: Boolean = false,
    val cookingMinutes: Int = 0,
    val creditsText: String = "",
    val dairyFree: Boolean = false,
    val dishTypes: List<String> = listOf(),
    val extendedIngredients: List<ExtendedIngredient> = listOf(),
    val glutenFree: Boolean = false,
    val healthScore: Int = 0,
    val id: Int = 0,
    val image: String = "",
    val imageType: String = "",
    val license: String = "",
    val likes: Int = 0,
    val pricePerServing: Double = 0.0,
    val readyInMinutes: Int = 0,
    val servings: Int = 0,
    val sourceName: String = "",
    val sourceUrl: String = "",
    val summary: String = "",
    val title: String = "",
    val vegan: Boolean = false,
    val vegetarian: Boolean = false,
    val veryHealthy: Boolean = false,
    val veryPopular: Boolean = false,
)

fun Result.toRecipe(): Recipe {
    return Recipe(
        id = id,
        instructions = analyzedInstructions,
        author = author,
        aggregateLikes = aggregateLikes,
        cheap = cheap,
        time = readyInMinutes,
        ingredients = extendedIngredients,
        healthScore = healthScore,
        image = image,
        imageType = imageType,
        likes = likes,
        readyInMinutes = readyInMinutes,
        sourceName = sourceName,
        sourceUrl = sourceUrl,
        summary = summary,
        title = title,
        vegan = vegan,
        vegetarian = vegetarian,
        veryPopular = veryPopular
    )
}