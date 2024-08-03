package com.ulises.addevent.navigation

import com.ulises.common.navigation.Screen

data object AddEditCountdownScreen: Screen {

    const val argumentKey = "item"
    override val route: String
        get() = "addCountDown/{$argumentKey}"

    fun createRoute(countdownId: String? = null) = "addCountdown/$countdownId"
}