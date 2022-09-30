package com.techK.foodium.data.databse

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.techK.foodium.data.databse.dao.RecipeDao
import com.techK.foodium.data.databse.typeconverters.DataConverters
import com.techK.foodium.domain.entities.Recipe

@Database(entities = [Recipe::class], version = 3, exportSchema = false)
@TypeConverters(DataConverters::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun getRecipeDao(): RecipeDao

}