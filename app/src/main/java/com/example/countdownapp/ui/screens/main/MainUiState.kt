package com.example.countdownapp.ui.screens.main

import com.example.domain.models.CountdownDate

data class MainUiState(
    val loading: Boolean = true,
    val countdownItems: List<CountdownDate>? = null,
    val error: Boolean = false
)