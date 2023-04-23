package com.example.countdownapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.countdownapp.data.database.dao.CountdownDao
import com.example.countdownapp.data.database.entities.Countdown
import com.example.countdownapp.data.database.entities.DateConverter

@Database(entities = [Countdown::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class CountdownDatabase : RoomDatabase() {
    abstract fun counterDao(): CountdownDao
}