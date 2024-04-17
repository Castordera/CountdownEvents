package com.ulises.data

import kotlinx.coroutines.flow.Flow

interface DataStorePreferences<T> {
    suspend fun save(key: String, value: T)
    fun get(key: String): Flow<T>
}