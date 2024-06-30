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
import com.ulises.addevent.ui.AddEventRoute
import com.ulises.addevent.navigation.AddEditCountdownScreen
import com.ulises.common.navigation.Screen
import com.ulises.event_detail.ui.CountdownDetailRoute
import com.ulises.list.ui.screens.CountDownRoute
import com.ulises.event_detail.navigation.CountdownDetailScreen
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
                onNavigateToDetail = { item ->
                    navController.navigate(CountdownDetailScreen.createRoute(item))
                },
                onNavigateToAdd = {
                    navController.navigate(AddEditCountdownScreen.createRoute())
                }
            )
        }
        composable(
            route = CountdownDetailScreen.route,
            arguments = listOf(navArgument(CountdownDetailScreen.argumentKey) { type = NavType.StringType }),
        ) {
            CountdownDetailRoute(
                viewModel = hiltViewModel(),
                onBackPress = { navController.popBackStack() },
                onEditItem = {
                    navController.navigate(AddEditCountdownScreen.createRoute(it))
                },
            )
        }
        composable(
            route = AddEditCountdownScreen.route,
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