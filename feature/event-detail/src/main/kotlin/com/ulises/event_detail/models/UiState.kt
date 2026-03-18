package com.ulises.event_detail.models

import com.example.domain.models.CountdownDate

data class UiState(
    val message: String? = null,
    val forceBack: Boolean = false,
    val countdownDate: CountdownDate? = null,
)