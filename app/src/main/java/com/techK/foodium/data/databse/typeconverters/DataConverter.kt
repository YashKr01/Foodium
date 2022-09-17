package com.techK.foodium.data.databse.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techK.foodium.data.response.AnalyzedInstruction
import com.techK.foodium.data.response.ExtendedIngredient
import com.techK.foodium.data.response.Step

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