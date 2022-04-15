package com.example.foodium.data.database.conveters

import androidx.room.TypeConverter
import com.example.foodium.data.network.model.AnalyzedInstruction
import com.example.foodium.data.network.model.ExtendedIngredient
import com.example.foodium.data.network.model.Step
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverters {

    @TypeConverter
    fun fromInstructionsList(list: List<AnalyzedInstruction>): String {
        val type = object : TypeToken<List<AnalyzedInstruction>>() {}.type
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun toInstructionsList(list: String): List<AnalyzedInstruction> {
        val type = object : TypeToken<List<AnalyzedInstruction>>() {}.type
        return Gson().fromJson(list, type)
    }

    @TypeConverter
    fun fromIngredientsList(list: List<ExtendedIngredient>): String {
        val type = object : TypeToken<List<ExtendedIngredient>>() {}.type
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun toIngredientsList(list: String): List<ExtendedIngredient> {
        val type = object : TypeToken<List<ExtendedIngredient>>() {}.type
        return Gson().fromJson(list, type)
    }

    @TypeConverter
    fun fromStepsList(list: List<Step>): String {
        val type = object : TypeToken<List<Step>>() {}.type
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun toStepsList(list: String): List<Step> {
        val type = object : TypeToken<List<Step>>() {}.type
        return Gson().fromJson(list, type)
    }

}