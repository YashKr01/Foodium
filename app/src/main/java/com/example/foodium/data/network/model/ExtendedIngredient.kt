package com.example.foodium.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExtendedIngredient(
    val aisle: String,
    val amount: Double,
    val consistency: String,
    val id: Int,
    val image: String,
    val name: String,
    val nameClean: String,
    val original: String,
    val originalName: String,
    val unit: String
) : Parcelable