package com.ulises.common.database.entities

import androidx.room.TypeConverter
import com.example.domain.enums.DateDisplayType

class DateDisplayConverter {

    @TypeConverter
    fun toDateDisplayType(value: String) = enumValueOf<DateDisplayType>(value)

    @TypeConverter
    fun fromDateDisplayType(value: DateDisplayType) = value.name
}