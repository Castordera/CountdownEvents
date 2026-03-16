package com.ulises.event_detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ulises.common.navigation.Screen
import com.ulises.event_detail.Action
import com.ulises.event_detail.models.DetailUiState
import com.ulises.usecase.countdown.AddEventUseCase
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
    getCountdownDate: GetEventUseCase,
    private val addEventUseCase: AddEventUseCase,
) : ViewModel() {

    private val params = savedStateHandle.toRoute<Screen.CountdownDetail>()
    val uiState = getCountdownDate(params.countdownId)
        .map {
            DetailUiState(
                countdownDate = it,
            )
        }.catch {
            Timber.d(it, "Error getting event with Id: ${params.countdownId}")
            emit(DetailUiState(error = it.localizedMessage))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailUiState()
        )

    fun onHandleAction(action: Action) {
        when (action) {
//            Actions.Interaction.DuplicateEvent -> onDuplicateEvent()
            else -> {}
        }
    }
}