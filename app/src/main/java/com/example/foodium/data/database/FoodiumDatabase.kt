package com.example.foodium.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodium.data.database.conveters.DataConverters
import com.example.foodium.data.database.model.RecipeEntity
import com.example.foodium.data.network.model.FoodJoke

@Database(
    entities = [RecipeEntity::class, FoodJoke::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverters::class)
abstract class FoodiumDatabase : RoomDatabase() {

    abstract fun recipesDao(): RecipeDao

}
