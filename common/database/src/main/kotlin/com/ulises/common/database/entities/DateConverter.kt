package com.ulises.common.database.entities

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateConverter {

    @TypeConverter
    fun fromString(value: String): LocalDate {
        return LocalDate.parse(value)
    }

    @TypeConverter
    fun dateToString(date: LocalDate): String {
        return date.format(DateTimeFormatter.ISO_DATE)
    }
}