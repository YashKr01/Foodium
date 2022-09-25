package com.techK.foodium.data.datastore

import com.techK.foodium.domain.enums.SortOrder
import kotlinx.coroutines.flow.Flow

interface RecipesDataStore {

    val sortOrder: Flow<SortOrder>

    suspend fun getRefreshQuery(): Flow<Boolean>

    suspend fun setRefresh(refresh: Boolean)

    suspend fun setSortOrder(sortOrder: SortOrder)

}