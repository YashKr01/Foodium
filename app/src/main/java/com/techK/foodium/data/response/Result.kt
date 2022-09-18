package com.techK.foodium.data.response

import com.google.gson.annotations.SerializedName
import com.techK.foodium.domain.entities.Recipe

data class Result(
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int,
    @SerializedName("analyzedInstructions")
    val analyzedInstructions: List<AnalyzedInstruction>,
    @SerializedName("author")
    val author: String?,
    @SerializedName("cheap")
    val cheap: Boolean,
    @SerializedName("cookingMinutes")
    val cookingMinutes: Int,
    @SerializedName("creditsText")
    val creditsText: String,
    @SerializedName("cuisines")
    val cuisines: List<String>,
    @SerializedName("dairyFree")
    val dairyFree: Boolean,
    @SerializedName("diets")
    val diets: List<String>,
    @SerializedName("dishTypes")
    val dishTypes: List<String>,
    @SerializedName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient>,
    @SerializedName("gaps")
    val gaps: String,
    @SerializedName("glutenFree")
    val glutenFree: Boolean,
    @SerializedName("healthScore")
    val healthScore: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("imageType")
    val imageType: String,
    @SerializedName("license")
    val license: String?,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("lowFodmap")
    val lowFodmap: Boolean,
    @SerializedName("missedIngredientCount")
    val missedIngredientCount: Int,
    @SerializedName("missedIngredients")
    val missedIngredients: List<MissedIngredient>,
    @SerializedName("occasions")
    val occasions: List<String>,
    @SerializedName("preparationMinutes")
    val preparationMinutes: Int,
    @SerializedName("pricePerServing")
    val pricePerServing: Double,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int,
    @SerializedName("servings")
    val servings: Int,
    @SerializedName("sourceName")
    val sourceName: String,
    @SerializedName("sourceUrl")
    val sourceUrl: String,
    @SerializedName("spoonacularSourceUrl")
    val spoonacularSourceUrl: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("sustainable")
    val sustainable: Boolean,
    @SerializedName("title")
    val title: String,
    @SerializedName("unusedIngredients")
    val unusedIngredients: List<Any>,
    @SerializedName("usedIngredientCount")
    val usedIngredientCount: Int,
    @SerializedName("usedIngredients")
    val usedIngredients: List<Any>,
    @SerializedName("vegan")
    val vegan: Boolean,
    @SerializedName("vegetarian")
    val vegetarian: Boolean,
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean,
    @SerializedName("veryPopular")
    val veryPopular: Boolean,
    @SerializedName("weightWatcherSmartPoints")
    val weightWatcherSmartPoints: Int,
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