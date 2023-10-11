package com.ulises.event_detail.ui

import com.example.domain.models.CountdownDate

data class DetailUiState(
    val error: String? = null,
    val countdownDate: CountdownDate? = null
)
