package com.techK.foodium.data.databse.dao

import androidx.room.*
import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.enums.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes")
    fun getSavedRecipesByOrder(sortOrder: SortOrder): Flow<List<Recipe>> =
        when (sortOrder) {
            SortOrder.BY_NAME -> sortSavedRecipesByName()
            SortOrder.BY_LIKES -> sortSavedRecipesByLikes()
            SortOrder.BY_TIME -> sortSavedRecipesByTime()
        }

    @Query("SELECT * FROM recipes")
    fun getSavedRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes ORDER BY title ASC")
    fun sortSavedRecipesByName(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes ORDER BY aggregateLikes DESC")
    fun sortSavedRecipesByLikes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes ORDER BY readyInMinutes ASC")
    fun sortSavedRecipesByTime(): Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("DELETE FROM recipes")
    suspend fun deleteAllRecipes()

}