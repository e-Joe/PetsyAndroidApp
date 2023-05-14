package com.bytecode.petsy.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bytecode.petsy.presentation.ui.screens.loginflow.passwordscreens.forgotpassword.ForgotPasswordScreen
import com.bytecode.petsy.presentation.ui.screens.loginflow.passwordscreens.passwordrequestedscreen.ForgotPasswordRequestedScreen
import com.bytecode.petsy.presentation.ui.screens.loginflow.landing.LandingScreen
import com.bytecode.petsy.presentation.ui.screens.loginflow.login.LoginScreen
import com.bytecode.petsy.presentation.ui.screens.loginflow.passwordscreens.forgotpassword.ForgotPasswordViewModel
import com.bytecode.petsy.presentation.ui.screens.loginflow.passwordscreens.resetpassword.ResetPasswordScreen
import com.bytecode.petsy.presentation.ui.screens.loginflow.passwordscreens.resetpassword.ResetPasswordViewModel
import com.bytecode.petsy.presentation.ui.screens.loginflow.register.*
import com.bytecode.petsy.presentation.ui.screens.loginflow.register.dogs.DogsNameScreen
import com.bytecode.petsy.presentation.ui.screens.loginflow.splash.SplashScreen

/**
 * Creates a Composable function that sets up the navigation graph for the app using Jetpack Compose Navigation.
 * @param navController The NavHostController used for navigation.
 *
 * @author Ilija Vucetic
 */
@Composable
fun LoginFlowGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = LoginFlowScreen.LandingScreen.route
    ) {

        composable(route = LoginFlowScreen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(route = LoginFlowScreen.LandingScreen.route) {
            LandingScreen(navController = navController)
        }

        composable(route = LoginFlowScreen.RegisterFirstScreen.route) {
            val viewModel: RegisterViewModel = hiltViewModel(it)
            RegisterFirstScreen(navController = navController, viewModel)
        }

        composable(route = LoginFlowScreen.RegisterSecondScreen.route) {
            val viewModel: RegisterViewModel = hiltViewModel(navController.previousBackStackEntry!!)
            RegisterSecondScreen(navController = navController, viewModel)
        }

        composable(route = LoginFlowScreen.DogsNameScreen.route) {
            val viewModel: RegisterViewModel = hiltViewModel(navController.previousBackStackEntry!!)
            DogsNameScreen(navController = navController, viewModel)
        }

        composable(route = LoginFlowScreen.VerifyEmailScreen.route) {
            VerifyEmailScreen(navController = navController)
        }

        composable(route = LoginFlowScreen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(route = LoginFlowScreen.ForgotPasswordScreen.route) {
            val viewModel: ForgotPasswordViewModel = hiltViewModel(it)
            ForgotPasswordScreen(navController = navController,viewModel)
        }

        composable(route = LoginFlowScreen.ForgotPasswordRequestedScreen.route) {
            ForgotPasswordRequestedScreen(navController = navController)
        }

        composable(route = LoginFlowScreen.ResetPasswordScreen.route) {
            val viewModel: ResetPasswordViewModel = hiltViewModel(it)
            ResetPasswordScreen(navController = navController,viewModel)
        }


    }
}