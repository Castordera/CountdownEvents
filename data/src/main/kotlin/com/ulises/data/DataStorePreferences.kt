package com.ulises.data

import kotlinx.coroutines.flow.Flow

interface DataStorePreferences<T> {
    suspend fun save(value: T)
    suspend fun getValue(): Flow<T>
}