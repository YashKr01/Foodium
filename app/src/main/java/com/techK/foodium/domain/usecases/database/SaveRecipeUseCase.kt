package com.techK.foodium.domain.usecases.database

import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.repository.RecipeRepository
import javax.inject.Inject

class SaveRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository,
) {

    suspend operator fun invoke(recipe: Recipe) {
        repository.saveRecipe(recipe)
    }

}