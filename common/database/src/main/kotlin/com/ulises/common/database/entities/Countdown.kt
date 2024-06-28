package com.ulises.common.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.enums.DateDisplayType
import com.example.domain.models.CountdownDate
import java.time.LocalDateTime

@Entity
data class Countdown(
    @PrimaryKey val id: String,
    val name: String,
    val createdAt: String,
    val selectedDate: LocalDateTime,
    val dateDisplayType: DateDisplayType,
)

fun CountdownDate.toEntity() = Countdown(
    id = id,
    name = name,
    createdAt = createdAt,
    selectedDate = dateToCountdown,
    dateDisplayType = dateDisplayType,
)

fun Countdown.toDomain() = CountdownDate(
    id = id,
    name = name,
    createdAt = createdAt,
    dateToCountdown = selectedDate,
    dateDisplayType = dateDisplayType,
)