package com.example.countdownapp.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countdownapp.ui.common.remainingTime
import com.example.domain.models.CountdownDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountdownDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

//    private val countdownDate = savedStateHandle.getParam<CountdownDate>(NavArgs.Detail.key)

//    private val _uiState = MutableStateFlow(DetailUiState(eventName = countdownDate?.name.orEmpty()))
//    val uiState = _uiState.asStateFlow()
//
//    init {
//        calculateRemainingDatePeriods(countdownDate!!)
//    }
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
//    fun onRefreshTimeClick() {
//        calculateRemainingDatePeriods(countdownDate!!)
//    }
}