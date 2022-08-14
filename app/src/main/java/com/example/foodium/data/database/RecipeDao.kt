package com.example.foodium.data.database

import androidx.room.*
import com.example.foodium.data.database.model.RecipeEntity
import com.example.foodium.utils.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {


    @Query("SELECT * FROM recipes_table")
    fun getRecipesListByOrder(sortOrder: SortOrder): Flow<List<RecipeEntity>> =
        when (sortOrder) {
            SortOrder.BY_NAME -> sortByName()
            SortOrder.BY_LIKES -> sortByLikes()
            SortOrder.BY_TIME -> sortByTime()
        }

    @Query("SELECT * FROM recipes_table")
    fun getRecipesList(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes_table ORDER BY title ASC")
    fun sortByName(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes_table ORDER BY aggregateLikes DESC")
    fun sortByLikes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes_table ORDER BY readyInMinutes ASC")
    fun sortByTime(): Flow<List<RecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)

    @Query("DELETE FROM recipes_table")
    suspend fun deleteAllRecipes()

}