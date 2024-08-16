package com.ulises.common.database.datasource

import com.ulises.common.database.dao.CountdownDao
import com.ulises.common.database.entities.toDomain
import com.ulises.common.database.entities.toEntity
import com.example.domain.models.CountdownDate
import com.ulises.data.datasource.CountdownLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomCountdownDataSource @Inject constructor(
    private val countdownDao: CountdownDao
): CountdownLocalDataSource {

    override fun getAllCountdown(): Flow<List<CountdownDate>> {
        return countdownDao.getAllCounters().map { list -> list.map { it.toDomain() } }
    }

    override fun getEventsForYear(year: String): Flow<List<CountdownDate>> {
        return countdownDao.getEventsForYear("$year%").map { list -> list.map { it.toDomain() } }
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

    override suspend fun editEvent(event: CountdownDate) {
        countdownDao.updateEvent(event.toEntity())
    }

    override suspend fun deleteCountdowns(items: Set<String>) {
        countdownDao.deleteCountDowns(items)
    }

    override fun getYearsWithData(): Flow<List<String>> {
        return countdownDao.getYearsWithData().distinctUntilChanged()
    }
}