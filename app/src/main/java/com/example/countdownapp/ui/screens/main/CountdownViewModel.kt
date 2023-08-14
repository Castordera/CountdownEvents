package com.example.countdownapp.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulises.usecase.countdown.GetAllCountdownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CountdownViewModel @Inject constructor(
    private val getAllCountdownUseCase: GetAllCountdownUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        onLoadData()
    }

    private fun onLoadData() {
        viewModelScope.launch {
            getAllCountdownUseCase()
                .onStart { _uiState.update { it.copy(loading = true) } }
                .catch { error ->
                    Timber.e(error)
                    _uiState.update { it.copy(loading = false) }
                }
                .collect { items ->
                    Timber.d("Items stored: $items")
                    _uiState.update { it.copy(loading = false, countdownItems = items) }
                }
        }
    }

    fun onListChangeAdapter() {
        _uiState.update { it.copy(isGrid = !it.isGrid) }
    }
}
