package com.example.countdownapp.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countdownapp.data.DataStoreDataSource
import com.example.domain.models.CountdownDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountdownViewModel @Inject constructor(
    private val dataSource: DataStoreDataSource
): ViewModel() {

    private val items = listOf(
        CountdownDate(name = "Bebecilles", dateToCountdown = "2023-05-29T00:00:00"),
        CountdownDate(name = "Cumpleanos", dateToCountdown = "2023-07-28T00:00:00"),
        CountdownDate(name = "Vacaciones", dateToCountdown = "2023-05-26T00:00:00"),
        CountdownDate(name = "Navidad", dateToCountdown = "2023-12-25T00:00:00"),
        CountdownDate(name = "Otro Mas", dateToCountdown = "2025-01-01T00:00:00"),
        CountdownDate(name = "En un rato", dateToCountdown = "2023-03-11T15:47:00")
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
