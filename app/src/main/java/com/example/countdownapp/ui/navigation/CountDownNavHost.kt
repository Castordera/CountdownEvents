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
import com.ulises.addevent.AddEventRoute
import com.ulises.common.navigation.Screen
import com.ulises.event_detail.ui.CountdownDetailRoute
import com.ulises.list.ui.CountDownRoute
import com.ulises.components.toolbars.TopBarItem
import com.ulises.list.navigation.ListScreen

@Composable
fun CountDownNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: Screen = ListScreen
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        composable(
            route = ListScreen.route
        ) {
            CountDownRoute(
                toolbarActions = listOf(
                    TopBarItem("Add", R.drawable.ic_add_24) {
                        navController.navigate(Screens.AddCountDown.createEditRoute())
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
                onBackPress = { navController.popBackStack() },
                onEditItem = {
                    navController.navigate(Screens.AddCountDown.createEditRoute(it))
                },
            )
        }
        composable(
            route = Screens.AddCountDown.route,
            enterTransition = { slideInHorizontally() },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it }
                )
            }
        ) {
            AddEventRoute(
                viewModel = hiltViewModel(),
                onBackPress = { navController.popBackStack() }
            )
        }
    }
}