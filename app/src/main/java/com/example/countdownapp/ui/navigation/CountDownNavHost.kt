package com.example.countdownapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ulises.common.navigation.Screen
import com.ulises.event_detail.CountdownDetailScreen
import com.ulises.list.CountDownScreen

@Composable
fun CountDownNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Listing,
    ) {
        composable<Screen.Listing> {
            CountDownScreen(
                onNavigateToDetail = { item ->
                    navController.navigate(Screen.CountdownDetail(item.id))
                },
            )
        }
        composable<Screen.CountdownDetail> {
            CountdownDetailScreen(
                viewModel = hiltViewModel(),
                onBackPress = { navController.popBackStack() },
                onEditItem = {
                    navController.navigate(Screen.AddEditCountdown(it))
                },
            )
        }
    }
}