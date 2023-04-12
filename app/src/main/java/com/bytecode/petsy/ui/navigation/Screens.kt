package com.bytecode.petsy.ui.navigation

sealed class Screens(val route: String){
    object SplashScreen : Screens("splash_screen")
    object LandingScreen : Screens("landing_screen")
    object LoginScreen : Screens("login_screen")
    object ForgotPasswordScreen : Screens("forgot_password_screen")
    object RegisterScreen : Screens("register_screen")
}
