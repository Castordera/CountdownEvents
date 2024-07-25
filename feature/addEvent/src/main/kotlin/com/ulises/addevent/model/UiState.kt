package com.ulises.addevent.model

import androidx.compose.runtime.Stable
import java.time.LocalDateTime

@Stable
data class UiState(
    val isLoading: Boolean = true,
    val eventName: String = "",
    val goBack: Boolean = false,
    val dateTime: LocalDateTime? = null,
    val error: String? = null,
)