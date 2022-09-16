package com.techK.foodium.data.databse

import androidx.room.Database
import androidx.room.RoomDatabase
import com.techK.foodium.domain.entities.Recipe

@Database(entities = [Recipe::class], version = 2, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {

    abstract val dao: RecipeDao

}