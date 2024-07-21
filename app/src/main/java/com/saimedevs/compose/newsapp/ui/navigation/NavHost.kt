package com.saimedevs.compose.newsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saimedevs.compose.newsapp.ui.screens.HomeScreen
import com.saimedevs.compose.newsapp.viewmodel.NewsVM
import com.saimedevs.compose.newsapp.ui.screens.DetailScreen

@Composable
fun NavHost(
    navController: NavHostController,
    viewModel: NewsVM,
) {
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ) {
        composable(Screens.HomeScreen.route) {
            HomeScreen(navController, viewModel)
        }

        composable(Screens.DetailScreen.route) {
            DetailScreen(navController, viewModel)
        }

    }
}