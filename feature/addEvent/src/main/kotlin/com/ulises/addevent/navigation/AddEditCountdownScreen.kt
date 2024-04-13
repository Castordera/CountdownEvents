package com.ulises.addevent.navigation

import com.ulises.common.navigation.Screen

data object AddEditCountdownScreen: Screen {

    override val route: String
        get() = "addCountDown/{item}"

    fun createRoute(countdownId: String? = null) = "addCountdown/$countdownId"
}