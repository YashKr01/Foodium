package com.techK.foodium.domain.usecases.database

import com.techK.foodium.domain.repository.RecipeRepository
import javax.inject.Inject

class DeleteAllRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository,
) {

    suspend operator fun invoke() {
        repository.deleteAllRecipes()
    }

}