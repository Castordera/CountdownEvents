package com.ulises.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ulises.common.database.entities.Countdown
import kotlinx.coroutines.flow.Flow

@Dao
interface CountdownDao {

    @Query("SELECT * FROM Countdown ORDER BY selectedDate")
    fun getAllCounters(): Flow<List<Countdown>>

    @Query("SELECT * FROM Countdown WHERE id = :id")
    fun getCounter(id: String): Flow<Countdown>

    @Insert
    suspend fun addNewCountdown(item: Countdown)

    @Query("DELETE FROM Countdown WHERE id = :id")
    suspend fun deleteCountdownById(id: String)

    @Update
    suspend fun updateEvent(event: Countdown)

    @Query("DELETE FROM Countdown WHERE id IN (:items)")
    suspend fun deleteCountDowns(items: Set<String>)
}