package com.techK.foodium.domain.usecases.database

import com.techK.foodium.domain.entities.Recipe
import com.techK.foodium.domain.enums.SortOrder
import com.techK.foodium.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecipesByOrderUseCase @Inject constructor(
    private val repository: RecipeRepository,
) {

    suspend operator fun invoke(sortOrder: SortOrder): Flow<List<Recipe>> = flow {
        repository.getRecipesByOrder(sortOrder).collect {
            emit(it)
        }
    }

}