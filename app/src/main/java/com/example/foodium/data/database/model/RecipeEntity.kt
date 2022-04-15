package com.example.foodium.data.database.model

import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodium.data.network.model.AnalyzedInstruction
import com.example.foodium.data.network.model.ExtendedIngredient
import com.example.foodium.utils.Constants.RECIPE_TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = RECIPE_TABLE_NAME)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    val recipeId: Int,
    @Nullable
    val instructions: List<AnalyzedInstruction>?,
    val aggregateLikes: Int,
    val cheap: Boolean,
    val dairyFree: Boolean,
    @Nullable
    val extendedIngredients: List<ExtendedIngredient>?,
    val glutenFree: Boolean,
    val image: String,
    val readyInMinutes: Int,
    @Nullable
    val sourceName: String?,
    val sourceUrl: String,
    val summary: String,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val popular: Boolean,
    val saved: Boolean
) : Parcelable
