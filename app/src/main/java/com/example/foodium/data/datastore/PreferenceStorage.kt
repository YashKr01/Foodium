package com.example.foodium.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodium.utils.Constants.REFRESH_LIST
import com.example.foodium.utils.Constants.USER_PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface PreferenceStorageImpl {

    suspend fun setRefresh(refresh: Boolean)

    val refreshList: Flow<Boolean>

}

@Singleton
class PreferenceStorage
@Inject constructor(@ApplicationContext private val context: Context) : PreferenceStorageImpl {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    override suspend fun setRefresh(refresh: Boolean) {
        context.dataStore.edit {
            it[REFRESH_LIST] = refresh
        }
    }

    override val refreshList: Flow<Boolean>
        get() = context.dataStore.data.map {
            it[REFRESH_LIST] ?: false
        }
}