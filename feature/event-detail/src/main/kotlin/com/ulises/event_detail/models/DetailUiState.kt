package com.ulises.event_detail.models

import com.example.domain.models.CountdownDate

data class DetailUiState(
    val error: String? = null,
    val countdownDate: CountdownDate? = null,
    val dayDetail: DayDetail? = null,
)

data class DayDetail(
    val years: Int,
    val days: Int,
    val hours: Int,
    val minutes: Int,
    val isPast: Boolean,
)