package com.bytecode.petsy.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bytecode.petsy.presentation.ui.screens.loginflow.register.RegisterViewModel
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowViewModel
import com.bytecode.petsy.presentation.ui.screens.mainflow.brushing.BrushingScreen
import com.bytecode.petsy.presentation.ui.screens.mainflow.dashboard.DashboardScreen
import com.bytecode.petsy.presentation.ui.screens.mainflow.profile.ProfileScreen

@Composable
fun MainFlowNavGraph(navController: NavHostController, mainFlowViewModel: MainFlowViewModel) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.DashboardScreen.route
    ) {

        composable(route = BottomBarScreen.DashboardScreen.route) {
            DashboardScreen(mainFlowViewModel)
        }
        composable(route = BottomBarScreen.BrushingScreen.route) {
            BrushingScreen(mainFlowViewModel)
        }
        composable(route = BottomBarScreen.ProfileScreen.route) {
            ProfileScreen()
        }
//        detailsNavGraph(navController = navController) //TODO add profile graph
    }
}