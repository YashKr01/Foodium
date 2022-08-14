package com.example.foodium.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodium.utils.Constants.REFRESH_LIST
import com.example.foodium.utils.Constants.SORT_PREFERENCE
import com.example.foodium.utils.Constants.USER_PREFERENCES_NAME
import com.example.foodium.utils.SortOrder
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface PreferenceStorageImpl {

    val refreshList: Flow<Boolean>

    val sortOrder: Flow<SortOrder>

    suspend fun setRefresh(refresh: Boolean)

    suspend fun setSortOrder(sortOrder: SortOrder)

}

@Singleton
class PreferenceStorage @Inject constructor(
    @ApplicationContext private val context: Context,
) : PreferenceStorageImpl {

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

    override val refreshList: Flow<Boolean>
        get() = context.dataStore.data.map {
            it[REFRESH_LIST] ?: false
        }

    override val sortOrder: Flow<SortOrder>
        get() = context.dataStore.data.map {
            SortOrder.valueOf(it[SORT_PREFERENCE] ?: SortOrder.BY_NAME.name)
        }

}