package com.techK.foodium.data.databse.dao

import androidx.room.*
import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.enums.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes")
    fun getRecipesListByOrder(sortOrder: SortOrder): Flow<List<Recipe>> =
        when (sortOrder) {
            SortOrder.BY_NAME -> sortByName()
            SortOrder.BY_LIKES -> sortByLikes()
            SortOrder.BY_TIME -> sortByTime()
        }

    @Query("SELECT * FROM recipes")
    fun getRecipesList(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes ORDER BY title ASC")
    fun sortByName(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes ORDER BY aggregateLikes DESC")
    fun sortByLikes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes ORDER BY readyInMinutes ASC")
    fun sortByTime(): Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("DELETE FROM recipes")
    suspend fun deleteAllRecipes()

}