package com.example.foodium.data.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodium.utils.Constants.JOKE_TABLE_NAME
import com.google.gson.annotations.SerializedName

@Entity(tableName = JOKE_TABLE_NAME)
data class FoodJoke(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("text")
    val text: String
)