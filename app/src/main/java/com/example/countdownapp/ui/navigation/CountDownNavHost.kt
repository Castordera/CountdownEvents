package com.example.countdownapp.ui.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.countdownapp.R
import com.example.countdownapp.ui.screens.add.AddEventRoute
import com.example.countdownapp.ui.screens.detail.CountdownDetailRoute
import com.example.countdownapp.ui.screens.main.CountDownRoute
import com.ulises.components.toolbars.TopBarItem

@Composable
fun CountDownNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: Screens = Screens.Home
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        composable(
            route = Screens.Home.route
        ) {
            CountDownRoute(
                toolbarActions = listOf(
                    TopBarItem("Add", R.drawable.ic_add_24) {
                        navController.navigate(Screens.AddCountDown.route)
                    }
                ),
                onNavigateToDetail = { item ->
                    navController.navigate(Screens.DetailCountDown.createRoute(item))
                }
            )
        }
        composable(
            route = Screens.DetailCountDown.route,
            arguments = listOf(navArgument(NavArgs.Detail.key) { type = NavType.StringType }),
        ) {
            CountdownDetailRoute(
                viewModel = hiltViewModel(),
                onBackPress = { navController.popBackStack() }
            )
        }
        composable(
            route = Screens.AddCountDown.route,
            enterTransition = { slideInHorizontally() },
            exitTransition = { slideOutHorizontally(
                targetOffsetX = { -it }
            ) }
        ) {
            AddEventRoute(
                viewModel = hiltViewModel(),
                onBackPress = { navController.popBackStack() }
            )
        }
    }
}