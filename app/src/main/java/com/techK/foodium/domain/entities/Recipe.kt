package com.techK.foodium.domain.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.techK.foodium.data.response.AnalyzedInstruction
import com.techK.foodium.data.response.ExtendedIngredient
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "RECIPES")
data class Recipe(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val instructions: List<AnalyzedInstruction>,
    val author: String?,
    val aggregateLikes: Int,
    val cheap: Boolean,
    val time: Int,
    val ingredients: List<ExtendedIngredient>,
    val healthScore: Int,
    val image: String,
    val imageType: String,
    val likes: Int,
    val readyInMinutes: Int,
    val sourceName: String,
    val sourceUrl: String,
    val summary: String,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryPopular: Boolean,
    var saved: Boolean = false,
) : Parcelable
