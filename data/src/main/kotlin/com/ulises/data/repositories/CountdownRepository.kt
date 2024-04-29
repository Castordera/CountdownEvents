package com.ulises.data.repositories

import com.example.domain.models.CountdownDate
import kotlinx.coroutines.flow.Flow

interface CountdownRepository {
    fun getAllCountdown(): Flow<List<CountdownDate>>
    fun getCountdown(id: String): Flow<CountdownDate>
    suspend fun addNewCountdown(item: CountdownDate)
    suspend fun deleteCountdown(id: String)
    suspend fun deleteCountdown(items: Set<String>)
    suspend fun editEvent(event: CountdownDate)
}