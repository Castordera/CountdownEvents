package com.ulises.addevent

import java.time.LocalDateTime

data class UiState(
    val isLoading: Boolean = true,
    val eventName: String = "",
    val goBack: Boolean = false,
    val dateDialogVisible: Boolean = false,
    val dateTime: LocalDateTime? = null,
    val saveButtonEnabled: Boolean = false
)