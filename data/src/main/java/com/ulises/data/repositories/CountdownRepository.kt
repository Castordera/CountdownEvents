package com.ulises.data.repositories

import com.example.domain.models.CountdownDate
import kotlinx.coroutines.flow.Flow

interface CountdownRepository {
    fun getAllCountdown(): Flow<List<CountdownDate>>
    suspend fun getCountdown(id: String): CountdownDate
    suspend fun addNewCountdown(item: CountdownDate)
    suspend fun deleteCountdown(id: String)
}