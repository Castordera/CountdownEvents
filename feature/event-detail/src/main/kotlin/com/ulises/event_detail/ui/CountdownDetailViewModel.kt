package com.ulises.event_detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulises.event_detail.models.DetailUiState
import com.ulises.event_detail.navigation.CountdownDetailScreen
import com.ulises.usecase.countdown.GetEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CountdownDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getCountdownDate: GetEventUseCase
) : ViewModel() {

    private val countdownDate: String = savedStateHandle[CountdownDetailScreen.argumentKey] ?: ""

    val uiState = getCountdownDate(countdownDate)
        .map { DetailUiState(countdownDate = it) }
        .catch {
            Timber.d("Error getting event with Id: $countdownDate")
            emit(DetailUiState(error = it.localizedMessage))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailUiState()
        )
}