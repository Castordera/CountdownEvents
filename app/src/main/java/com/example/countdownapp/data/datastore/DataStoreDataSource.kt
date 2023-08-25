package com.example.countdownapp.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
): DataStorePreferences<Boolean> {

    override val key: Preferences.Key<Boolean>
        get() = booleanPreferencesKey(KEY_STORED_VALUES)

    override suspend fun save(value: Boolean) {
        dataStore.edit { settings ->
            settings[key] = value
        }
    }

    override suspend fun getValue(): Flow<Boolean> {
        return dataStore.data.map { settings -> settings[key] ?: false }.distinctUntilChanged()
    }

    private companion object {
        const val KEY_STORED_VALUES = "events-view-type"
    }
}