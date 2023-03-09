package com.example.countdownapp.ui.screens.detail

import androidx.lifecycle.ViewModel
import com.example.domain.models.CountdownDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CountdownDetailViewModel @Inject constructor (): ViewModel() {

    private val item = CountdownDate(
        name = "Bebecita",
        dateToCountdown = "2023-05-29T23:25:14.697982"
    )

    private val _uiState = MutableStateFlow(DetailUiState(item))
    val uiState = _uiState.asStateFlow()
}