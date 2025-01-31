package com.ulises.common.navigation

import kotlinx.serialization.Serializable

@Serializable sealed interface Screen {

    @Serializable
    data object Listing

    @Serializable
    data class CountdownDetail(val countdownId: String)

    @Serializable
    data class AddEditCountdown(val countdownId: String? = null)
}