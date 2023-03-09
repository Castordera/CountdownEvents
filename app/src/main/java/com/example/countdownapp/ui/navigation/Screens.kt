package com.example.countdownapp.ui.navigation

sealed class Screens(val route: String) {
    object Home: Screens("home")
    object DetailCountDown: Screens("detailCountDown/{itemId}")
    object AddCountDown: Screens("addCountDown")
}

fun Screens.DetailCountDown.getDecodedRoute(itemId: String): String {
    return route.replace("{itemId}", itemId)
}
