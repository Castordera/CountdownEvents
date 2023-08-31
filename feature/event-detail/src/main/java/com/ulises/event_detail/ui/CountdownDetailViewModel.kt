package com.ulises.event_detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulises.usecase.countdown.GetCountdownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CountdownDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCountdownDate: GetCountdownUseCase
) : ViewModel() {

    private val countdownDate = savedStateHandle.get<String>("item") ?: ""//Todo(Remove hardcoded key, should be NavArgs.Detail.key)

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getInitialData()
    }

    private fun getInitialData() {
        viewModelScope.launch {
            getCountdownDate(countdownDate)
                .catch { error ->
                    Timber.e(error, "Error getting Countdown item")
                    _uiState.update { it.copy(error = "This is my error") }
                }
                .collect { item ->
                    _uiState.update { it.copy(countdownDate = item) }
                }
        }
    }
//
//    private fun calculateRemainingDatePeriods(countdownDate: CountdownDate) {
//        viewModelScope.launch {
//            val dateTime = countdownDate.remainingTime
//            _uiState.update { it.copy(
//                remainingTime = dateTime.value,
//                remainingPeriod = dateTime.periodType
//            ) }
//        }
//    }
//
    fun onRefreshTimeClick() {
//        calculateRemainingDatePeriods(countdownDate!!)
    }
}