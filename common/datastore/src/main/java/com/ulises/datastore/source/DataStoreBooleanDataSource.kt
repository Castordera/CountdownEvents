package com.ulises.datastore.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.ulises.data.DataStorePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreBooleanDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStorePreferences<Boolean> {

    override suspend fun save(key: String, value: Boolean) {
        dataStore.edit { settings -> settings[booleanPreferencesKey(key)] = value }
    }

    override suspend fun get(key: String): Flow<Boolean> {
        return dataStore.data
            .map { settings -> settings[booleanPreferencesKey(key)] ?: false }
            .distinctUntilChanged()
    }
}