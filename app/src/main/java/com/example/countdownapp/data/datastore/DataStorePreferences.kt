package com.example.countdownapp.data.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStorePreferences<T> {
    val key: Preferences.Key<T>
    suspend fun save(value: T)
    suspend fun getValue(): Flow<T>
}