package com.ulises.datastore.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ulises.data.DataStorePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreStringDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStorePreferences<String> {

    override suspend fun save(key: String, value: String) {
        dataStore.edit { settings -> settings[stringPreferencesKey(key)] = value }
    }

    override fun get(key: String): Flow<String> {
        return dataStore.data
            .map { settings -> settings[stringPreferencesKey(key)].orEmpty() }
            .distinctUntilChanged()
    }
}