package com.example.countdownapp.ui.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ulises.addevent.ui.AddEventRoute
import com.ulises.common.navigation.Screen
import com.ulises.event_detail.ui.CountdownDetailRoute
import com.ulises.list.ui.route.CountDownRoute

@Composable
fun CountDownNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Listing,
    ) {
        composable<Screen.Listing> {
            CountDownRoute(
                onNavigateToDetail = { item ->
                    navController.navigate(Screen.CountdownDetail(item.id))
                },
                onNavigateToAdd = {
                    navController.navigate(Screen.AddEditCountdown())
                }
            )
        }
        composable<Screen.CountdownDetail> {
            CountdownDetailRoute(
                viewModel = hiltViewModel(),
                onBackPress = { navController.popBackStack() },
                onEditItem = {
                    navController.navigate(Screen.AddEditCountdown(it))
                },
            )
        }
        composable<Screen.AddEditCountdown>(
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