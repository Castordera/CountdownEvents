package com.example.countdownapp.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countdownapp.data.DataStoreDataSource
import com.example.domain.models.CountdownDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CountdownViewModel @Inject constructor(
    private val dataSource: DataStoreDataSource
) : ViewModel() {

    private val items = listOf(
        CountdownDate(
            id = "1111",
            name = "Bebecilles",
            dateToCountdown = LocalDateTime.parse("2023-05-29T00:00:00"),
            createdAt = ""
        ),
        CountdownDate(
            id = "2222",
            name = "Cumpleanos",
            dateToCountdown = LocalDateTime.parse("2023-07-28T00:00:00"),
            createdAt = ""
        ),
        CountdownDate(
            id = "3333",
            name = "Vacaciones",
            dateToCountdown = LocalDateTime.parse("2023-05-26T00:00:00"),
            createdAt = ""
        ),
        CountdownDate(
            id = "4444",
            name = "Navidad",
            dateToCountdown = LocalDateTime.parse("2023-12-25T00:00:00"),
            createdAt = ""
        ),
        CountdownDate(
            id = "5556",
            name = "Otro Mas",
            dateToCountdown = LocalDateTime.parse("2025-01-01T00:00:00"),
            createdAt = ""
        ),
        CountdownDate(
            id = "6666",
            name = "Pasadp",
            dateToCountdown = LocalDateTime.parse("2023-03-12T00:00:00"),
            createdAt = ""
        )
    )

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        onLoadData()
    }

    private fun onLoadData() {
        viewModelScope.launch(Dispatchers.Main) {
            flowOf(items)
                .onStart { _uiState.update { it.copy(loading = true) } }
                .catch { error ->
                    Timber.e(error)
                    _uiState.update { it.copy(loading = false) }
                }
                .collect {
                    _uiState.update { it.copy(loading = false, countdownItems = items) }
                }
        }
    }

    fun onListChangeAdapter() {
        _uiState.update { it.copy(isGrid = !it.isGrid) }
    }
}
