package com.ulises.list.navigation

import com.ulises.common.navigation.Screen

data object ListScreen: Screen {
    override val route: String
        get() = "home"
}