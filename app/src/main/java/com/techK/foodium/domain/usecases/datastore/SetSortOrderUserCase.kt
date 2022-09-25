package com.techK.foodium.domain.usecases.datastore

import com.techK.foodium.domain.enums.SortOrder
import com.techK.foodium.domain.repository.RecipeRepository
import javax.inject.Inject

class SetSortOrderUserCase @Inject constructor(
    private val repository: RecipeRepository,
) {

    suspend operator fun invoke(sortOrder: SortOrder) {
        repository.setSortOrder(sortOrder)
    }

}