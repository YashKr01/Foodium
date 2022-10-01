package com.techK.foodium.domain.usecases.database

import com.techK.foodium.domain.enums.SortOrder
import com.techK.foodium.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository,
) {

    suspend operator fun invoke(query: String, sortOrder: SortOrder) = flow {
        repository.searchRecipe(query.lowercase(), sortOrder).collect {
            emit(it)
        }
    }

}