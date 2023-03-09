package com.example.countdownapp.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.CountdownDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CountdownViewModel @Inject constructor(): ViewModel() {

    private val items = listOf(
        CountdownDate(name = "Bebecilles", dateToCountdown = "2023-05-29T23:25:14.697982"),
        CountdownDate(name = "Cumpleanos", dateToCountdown = "2023-07-28T23:25:14.697982"),
        CountdownDate(name = "Vacaciones", dateToCountdown = "2023-05-26T23:25:14.697982"),
        CountdownDate(name = "Navidad", dateToCountdown = "2023-12-25T23:25:14.697982"),
        CountdownDate(name = "Otro Mas", dateToCountdown = "2025-01-01T06:00:00.697982")
    )

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        onLoadData()
    }

    private fun onLoadData() {
        viewModelScope.launch {
            val data = items
            _uiState.update { it.copy(loading = false, countdownItems = data) }
        }
    }
}
