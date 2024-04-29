package com.ulises.data.datasource

import com.example.domain.models.CountdownDate
import kotlinx.coroutines.flow.Flow

interface CountdownLocalDataSource {
    fun getAllCountdown(): Flow<List<CountdownDate>>
    fun getCountdown(id: String): Flow<CountdownDate>
    suspend fun addNewCountdown(item: CountdownDate)
    suspend fun deleteCountdown(id: String)
    suspend fun deleteCountdowns(items: Set<String>)
    suspend fun editEvent(event: CountdownDate)
}