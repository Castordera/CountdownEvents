package com.ulises.list.ui

import com.example.domain.models.CountdownDate
import com.example.domain.CountdownSortType

data class MainUiState(
    val loading: Boolean = true,
    val countdownItems: List<CountdownDate>? = null,
    val dialogDeleteVisible: Boolean = false,
    val error: Boolean = false,
    val isGrid: Boolean = false,
    val sortType: CountdownSortType = CountdownSortType.NORMAL
)