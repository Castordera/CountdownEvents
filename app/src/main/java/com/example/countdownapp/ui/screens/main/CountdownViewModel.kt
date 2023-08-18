package com.example.countdownapp.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulises.usecase.countdown.DeleteCountdownUseCase
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
    private val getAllCountdownUseCase: GetAllCountdownUseCase,
    private val deleteCountdownUseCase: DeleteCountdownUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()
    //
    private var deleteItemId: String? = null

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

    fun onChangeDialogVisibility(visible: Boolean) {
        if (!visible) deleteItemId = null
        _uiState.update { it.copy(dialogDeleteVisible = visible) }
    }

    fun onRequestDeleteItem(id: String) {
        deleteItemId = id
        onChangeDialogVisibility(true)
    }

    fun onDeleteCountdownItem() {
        viewModelScope.launch {
            runCatching {
                checkNotNull(deleteItemId)
                deleteCountdownUseCase(deleteItemId!!)
            }.onFailure {
                Timber.e(it, "Error deleting countdown")
            }.onSuccess {
                onChangeDialogVisibility(false)
            }
        }
    }
}
