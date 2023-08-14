package com.example.countdownapp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    fun getEventsList() {
        val key = stringPreferencesKey(KEY_STORED_VALUES)
        dataStore.data.map { preferences ->
        }
    }

    suspend fun saveEvent() {
        dataStore.edit { preferences ->

        }
    }

    companion object {
        const val KEY_STORED_VALUES = "events-stored"
    }
}