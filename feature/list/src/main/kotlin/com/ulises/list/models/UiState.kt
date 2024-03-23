package com.ulises.list.models

import com.example.domain.models.CountdownDate
import com.example.domain.enums.CountdownSortType

data class UiState(
    val loading: Boolean = true,
    val countdownItems: List<CountdownDate>? = null,
    val dialogDeleteVisible: Boolean = false,
    val error: String? = null,
    val isGrid: Boolean = false,
    val sortType: CountdownSortType = CountdownSortType.NORMAL,
)