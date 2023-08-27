package com.example.countdownapp.ui.screens.detail

import com.example.domain.models.CountdownDate

data class DetailUiState(
    val error: String? = null,
    val countdownDate: CountdownDate? = null
)
