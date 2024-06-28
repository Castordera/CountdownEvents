package com.ulises.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ulises.common.database.dao.CountdownDao
import com.ulises.common.database.entities.Countdown
import com.ulises.common.database.entities.DateConverter
import com.ulises.common.database.entities.DateDisplayConverter

@Database(entities = [Countdown::class], version = 2, exportSchema = false)
@TypeConverters(DateConverter::class, DateDisplayConverter::class)
abstract class CountdownDatabase : RoomDatabase() {
    abstract fun counterDao(): CountdownDao
}