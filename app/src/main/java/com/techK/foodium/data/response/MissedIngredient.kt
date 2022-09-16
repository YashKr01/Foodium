package com.techK.foodium.data.response


import com.google.gson.annotations.SerializedName

data class MissedIngredient(
    @SerializedName("aisle")
    val aisle: String?,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("extendedName")
    val extendedName: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("meta")
    val meta: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("original")
    val original: String,
    @SerializedName("originalName")
    val originalName: String,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("unitLong")
    val unitLong: String,
    @SerializedName("unitShort")
    val unitShort: String
)