package com.bytecode.petsy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bytecode.petsy.ui.screens.loginflow.landing.LandingScreen
import com.bytecode.petsy.ui.screens.loginflow.register.RegisterScreen
import com.bytecode.petsy.ui.screens.loginflow.splash.SplashScreen

/**
 * Creates a Composable function that sets up the navigation graph for the app using Jetpack Compose Navigation.
 * @param navController The NavHostController used for navigation.
 *
 * @author Ilija Vucetic
 */
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.LandingScreen.route
    ) {

        composable(route = Screens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screens.LandingScreen.route) {
            LandingScreen(navController = navController)
        }

        composable(route = Screens.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
    }
}