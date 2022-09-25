package com.techK.foodium.domain.usecases.datastore

import com.techK.foodium.domain.repository.RecipeRepository
import javax.inject.Inject

class SetRefreshQueryUseCase @Inject constructor(
    private val repository: RecipeRepository,
) {

    suspend operator fun invoke(refresh: Boolean) {
        repository.setRefresh(refresh)
    }

}