package com.example.countdownapp.data.database.datasource

import com.example.countdownapp.data.database.dao.CountdownDao
import com.example.countdownapp.data.database.entities.toDomain
import com.example.countdownapp.data.database.entities.toEntity
import com.example.domain.models.CountdownDate
import com.ulises.data.datasource.CountdownLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomCountdownDataSource @Inject constructor(
    private val countdownDao: CountdownDao
): CountdownLocalDataSource {

    override fun getAllCountdown(): Flow<List<CountdownDate>> {
        return countdownDao.getAllCounters().map { list -> list.map { it.toDomain() } }
    }

    override fun getCountdown(id: String): Flow<CountdownDate> {
        return countdownDao.getCounter(id).map { it.toDomain() }
    }

    override suspend fun addNewCountdown(item: CountdownDate) {
        countdownDao.addNewCountdown(item.toEntity())
    }

    override suspend fun deleteCountdown(id: String) {
        countdownDao.deleteCountdownById(id)
    }
}