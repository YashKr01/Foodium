package com.techK.foodium.domain.usecases.datastore

import com.techK.foodium.domain.enums.SortOrder
import com.techK.foodium.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSortOrderUserCase @Inject constructor(
    private val repository: RecipeRepository,
) {

    suspend operator fun invoke(): Flow<SortOrder> = flow {
        repository.getSortOrder().collect {
            emit(it)
        }
    }

}