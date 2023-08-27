package com.example.countdownapp.ui.screens.add

import java.time.LocalDateTime

data class AddUiState(
    val eventName: String = "",
    val goBack: Boolean = false,
    val dateDialogVisible: Boolean = false,
    val dateTime: LocalDateTime,
    val saveButtonEnabled: Boolean = false
)