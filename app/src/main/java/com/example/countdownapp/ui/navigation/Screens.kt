package com.example.countdownapp.ui.navigation

import com.example.domain.models.CountdownDate

sealed class Screens(val route: String) {
    object Home : Screens("home")
    object DetailCountDown : Screens("detailCountDown/{${NavArgs.Detail.key}}") {
        fun createRoute(item: CountdownDate) = "detailCountDown/${item.id}"
    }
    object AddCountDown : Screens("addCountDown")
}

enum class NavArgs(val key: String) {
    Detail("item")
}
