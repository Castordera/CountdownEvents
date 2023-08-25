package com.example.countdownapp.ui.screens.utils

import com.example.countdownapp.ui.screens.main.CountdownSortType
import com.example.domain.models.CountdownDate
import java.time.LocalDateTime

val listItemsPreview = listOf(
    CountdownDate(
        id = "1111",
        name = "Bebecita",
        createdAt = "",
        dateToCountdown = LocalDateTime.parse("2023-05-29T00:00:00")
    ),
    CountdownDate(
        id = "2222",
        name = "This is a huge event name to test if the constraints really do their job, or not at all",
        createdAt = "",
        dateToCountdown = LocalDateTime.parse("2023-12-08T00:00:00")
    ),
    CountdownDate(
        id = "3333",
        name = "Birth Day 3",
        createdAt = "",
        dateToCountdown = LocalDateTime.parse("2023-12-08T00:00:00")
    )
)

val radioOptionsPreview = listOf(
    CountdownSortType.NORMAL,
    CountdownSortType.DATE
)