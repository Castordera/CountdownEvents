package com.ulises.event_detail.models

import com.example.domain.models.CountdownDate

data class DetailUiState(
    val error: String? = null,
    val countdownDate: CountdownDate? = null
)
