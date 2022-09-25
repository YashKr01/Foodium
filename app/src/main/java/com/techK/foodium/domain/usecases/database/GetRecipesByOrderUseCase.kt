package com.techK.foodium.domain.usecases.database

import com.techK.foodium.domain.enums.SortOrder
import com.techK.foodium.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecipesByOrderUseCase @Inject constructor(
    private val repository: RecipeRepository,
) {

    suspend operator fun invoke(sortOrder: SortOrder) = flow {
        repository.getRecipesByOrder(sortOrder).collect {
            emit(it)
        }
    }

}