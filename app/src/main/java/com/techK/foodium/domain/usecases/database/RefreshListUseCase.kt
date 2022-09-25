package com.techK.foodium.domain.usecases.database

import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshListUseCase @Inject constructor(
    private val repository: RecipeRepository,
) {

    suspend operator fun invoke(currentList: List<Recipe>): Flow<List<Recipe>> = flow{
        val savedList = repository.getSavedRecipes().first()
        val newList = currentList.map { recipe ->
            val isSaved = savedList.any { it.id == recipe.id }
            recipe.copy(saved = isSaved)
        }
        emit(newList)
    }

}