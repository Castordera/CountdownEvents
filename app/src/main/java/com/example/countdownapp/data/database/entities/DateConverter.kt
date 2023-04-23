package com.example.countdownapp.data.database.entities

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateConverter {

    @TypeConverter
    fun fromString(value: String): LocalDateTime {
        return LocalDateTime.parse(value)
    }

    @TypeConverter
    fun dateToString(date: LocalDateTime): String {
        return date.format(DateTimeFormatter.ISO_DATE_TIME)
    }
}