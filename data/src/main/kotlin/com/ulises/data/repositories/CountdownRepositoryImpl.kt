package com.ulises.data.repositories

import com.example.domain.models.CountdownDate
import com.ulises.data.datasource.CountdownLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CountdownRepositoryImpl @Inject constructor(
    private val localDataSource: CountdownLocalDataSource
) : CountdownRepository {

    override fun getAllCountdown(): Flow<List<CountdownDate>> {
        return localDataSource.getAllCountdown()
    }

    override fun getEventsForYear(year: String): Flow<List<CountdownDate>> {
        return localDataSource.getEventsForYear(year)
    }

    override fun getCountdown(id: String): Flow<CountdownDate> {
        return localDataSource.getCountdown(id)
    }

    override suspend fun addNewCountdown(item: CountdownDate) {
        localDataSource.addNewCountdown(item)
    }

    override suspend fun deleteCountdown(id: String) {
        localDataSource.deleteCountdown(id)
    }

    override suspend fun editEvent(event: CountdownDate) {
        localDataSource.editEvent(event)
    }

    override suspend fun deleteCountdown(items: Set<String>) {
        localDataSource.deleteCountdowns(items)
    }

    override fun getAllYearsWithItems(): Flow<List<String>> {
        return localDataSource.getYearsWithData()
    }
}