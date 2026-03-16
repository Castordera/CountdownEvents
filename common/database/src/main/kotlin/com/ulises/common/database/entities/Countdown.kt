package com.ulises.common.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.enums.DateDisplayType
import com.example.domain.models.CountdownDate
import java.time.LocalDate

@Entity
data class Countdown(
    @PrimaryKey val id: String,
    val name: String,
    val createdAt: String,
    val dateDisplayType: DateDisplayType,
    val date: LocalDate,
)

fun CountdownDate.toEntity() = Countdown(
    id = id,
    name = name,
    createdAt = createdAt,
    dateDisplayType = dateDisplayType,
    date = date,
)

fun Countdown.toDomain() = CountdownDate(
    id = id,
    name = name,
    createdAt = createdAt,
    dateDisplayType = dateDisplayType,
    date = date,
)