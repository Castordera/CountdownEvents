package com.ulises.preview_data

import com.example.domain.models.CountdownDate
import java.time.LocalDateTime

val listItemsPreview = listOf(
    getMockCountDown(
        id = "1111",
        name = "Bebecita",
        createdAt = "Lunes 10 de Enero",
        date = "2023-05-29T00:00:00",
    ),
    getMockCountDown(
        id = "2222",
        name = "This is a huge event name to test if the constraints really do their job, or not at all",
        createdAt = "",
        date = "2023-12-08T00:00:00",
    ),
    getMockCountDown(
        id = "3333",
        name = "Birth Day 3",
        createdAt = "",
        date = "2023-12-08T00:00:00",
    )
)

fun getMockCountDown(
    id: String = "123456789",
    name: String = "Demo Name",
    createdAt: String = "Random Day selected",
    date: String? = null
): CountdownDate {
    return CountdownDate(
        id = id,
        name = name,
        createdAt = createdAt,
        dateToCountdown = if (date.isNullOrBlank()) LocalDateTime.now() else LocalDateTime.parse(date)
    )
}