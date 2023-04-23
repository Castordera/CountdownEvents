package com.example.countdownapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.countdownapp.data.database.entities.Countdown
import kotlinx.coroutines.flow.Flow

@Dao
interface CountdownDao {

    @Query("SELECT * FROM Countdown")
    fun getAllCounters(): Flow<List<Countdown>>

    @Query("SELECT * FROM Countdown WHERE id = :id")
    suspend fun getCounter(id: String): Countdown

    @Insert
    suspend fun addNewCountdown(item: Countdown)

    @Query("DELETE FROM Countdown WHERE id = :id")
    suspend fun deleteCountdownById(id: String)
}