package com.example.foodium.data.database

import androidx.room.*
import com.example.foodium.data.database.model.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {


    @Query("SELECT * FROM RECIPE_DATABASE")
    fun getRecipesList(): Flow<List<RecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)

}