package com.ulises.preview_data

import com.example.domain.models.CountdownDate
import java.time.LocalDate
import java.time.LocalDateTime

val listItemsPreview = listOf(
    getMockCountDown(
        id = "1111",
        name = "Bebecita",
        createdAt = "Lunes 10 de Enero",
        realDate = "2025-12-08",
    ),
    getMockCountDown(
        id = "2222",
        name = "This is a huge event name to test if the constraints really do their job, or not at all",
        createdAt = "",
        realDate = "2026-12-08",
    ),
    getMockCountDown(
        id = "3333",
        name = "Birth Day 3",
        createdAt = "",
        realDate = "2026-12-08",
    )
)

fun getMockCountDown(
    id: String = "123456789",
    name: String = "Demo Name",
    createdAt: String = "Random Day selected",
    realDate: String? = null,
): CountdownDate {
    return CountdownDate(
        id = id,
        name = name,
        createdAt = createdAt,
        date = if (realDate.isNullOrBlank()) LocalDate.now() else LocalDate.parse(realDate)
    )
}