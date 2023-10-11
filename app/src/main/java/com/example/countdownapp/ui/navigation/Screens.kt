package com.example.countdownapp.ui.navigation

import com.example.domain.models.CountdownDate

sealed class Screens(val route: String) {
    data object Home : Screens("home")
    data object DetailCountDown : Screens("detailCountDown/{${NavArgs.Detail.key}}") {
        fun createRoute(item: CountdownDate) = "detailCountDown/${item.id}"
    }
    data object AddCountDown : Screens("addCountDown/{${NavArgs.Create.key}}") {
        fun createEditRoute(id: String? = null) = "addCountDown/$id"
    }
}

enum class NavArgs(val key: String) {
    Detail("item"),
    Create("item"),
}
