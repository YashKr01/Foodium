package com.techK.foodium.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.techK.foodium.domain.enums.SortOrder
import com.techK.foodium.domain.utils.Constants.REFRESH_LIST
import com.techK.foodium.domain.utils.Constants.SORT_PREFERENCE
import com.techK.foodium.domain.utils.Constants.USER_PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipesDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : RecipesDataStore {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    override suspend fun setRefresh(refresh: Boolean) {
        context.dataStore.edit {
            it[REFRESH_LIST] = refresh
        }
    }

    override suspend fun setSortOrder(sortOrder: SortOrder) {
        context.dataStore.edit {
            it[SORT_PREFERENCE] = sortOrder.name
        }
    }

    override suspend fun getRefreshQuery(): Flow<Boolean> = flow {
        context.dataStore.data.map {
            it[REFRESH_LIST] ?: false
        }.collect { emit(it) }
    }

    override val sortOrder: Flow<SortOrder>
        get() = context.dataStore.data.map {
            SortOrder.valueOf(it[SORT_PREFERENCE] ?: SortOrder.BY_NAME.name)
        }

}