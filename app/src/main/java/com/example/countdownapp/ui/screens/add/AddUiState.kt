package com.example.countdownapp.ui.screens.add

data class AddUiState(
    val eventName: String = "",
    val date: String = "",
    val goBack: Boolean = false,
    val dateDialogVisible: Boolean = false
)