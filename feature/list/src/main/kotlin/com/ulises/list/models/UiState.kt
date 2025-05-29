package com.ulises.list.models

import com.example.domain.enums.CountdownSortType
import com.example.domain.models.CountdownDate
import com.example.domain.models.YearsData

data class UiState(
    val loading: Boolean = true,
    val activeItems: List<CountdownDate>? = null,
    val passedItems: List<CountdownDate>? = null,
    val error: String? = null,
    val isGrid: Boolean = false,
    val sortType: CountdownSortType = CountdownSortType.NORMAL,
    val selectedEvents: Set<String> = emptySet(),
    val isSelectionMode: Boolean = false,
    val yearsData: YearsData? = null,
)