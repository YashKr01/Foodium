package com.techK.foodium.domain.usecases.datastore

import com.techK.foodium.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRefreshQueryUseCase @Inject constructor(
    private val repository: RecipeRepository,
) {

    suspend operator fun invoke() = flow {
        repository.getRefreshQuery().collect {
            emit(it)
        }
    }

}