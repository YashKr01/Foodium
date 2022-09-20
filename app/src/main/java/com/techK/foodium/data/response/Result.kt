package com.techK.foodium.data.response

import com.google.gson.annotations.SerializedName
import com.techK.foodium.domain.entities.Recipe

data class Result(
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int = 0,
    @SerializedName("analyzedInstructions")
    val analyzedInstructions: List<AnalyzedInstruction> = listOf(),
    @SerializedName("author")
    val author: String = "",
    @SerializedName("cheap")
    val cheap: Boolean = false,
    @SerializedName("cookingMinutes")
    val cookingMinutes: Int = 0,
    @SerializedName("creditsText")
    val creditsText: String = "",
    @SerializedName("cuisines")
    val cuisines: List<String> = listOf(),
    @SerializedName("dairyFree")
    val dairyFree: Boolean = false,
    @SerializedName("diets")
    val diets: List<String> = listOf(),
    @SerializedName("dishTypes")
    val dishTypes: List<String> = listOf(),
    @SerializedName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient> = listOf(),
    @SerializedName("gaps")
    val gaps: String = "",
    @SerializedName("glutenFree")
    val glutenFree: Boolean = false,
    @SerializedName("healthScore")
    val healthScore: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("imageType")
    val imageType: String = "",
    @SerializedName("license")
    val license: String = "",
    @SerializedName("likes")
    val likes: Int = 0,
    @SerializedName("lowFodmap")
    val lowFodmap: Boolean = false,
    @SerializedName("missedIngredientCount")
    val missedIngredientCount: Int = 0,
    @SerializedName("occasions")
    val occasions: List<String> = listOf(),
    @SerializedName("preparationMinutes")
    val preparationMinutes: Int = 0,
    @SerializedName("pricePerServing")
    val pricePerServing: Double = 0.0,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int = 0,
    @SerializedName("servings")
    val servings: Int = 0,
    @SerializedName("sourceName")
    val sourceName: String = "",
    @SerializedName("sourceUrl")
    val sourceUrl: String = "",
    @SerializedName("spoonacularSourceUrl")
    val spoonacularSourceUrl: String = "",
    @SerializedName("summary")
    val summary: String = "",
    @SerializedName("sustainable")
    val sustainable: Boolean = false,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("unusedIngredients")
    val unusedIngredients: List<Any> = listOf(),
    @SerializedName("usedIngredientCount")
    val usedIngredientCount: Int = 0,
    @SerializedName("usedIngredients")
    val usedIngredients: List<Any> = listOf(),
    @SerializedName("vegan")
    val vegan: Boolean = false,
    @SerializedName("vegetarian")
    val vegetarian: Boolean = false,
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean = false,
    @SerializedName("veryPopular")
    val veryPopular: Boolean = false,
    @SerializedName("weightWatcherSmartPoints")
    val weightWatcherSmartPoints: Int = 0,
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