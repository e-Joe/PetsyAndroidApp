package com.bytecode.petsy.presentation.ui.navigation

/**
 * The Screens class is a Kotlin sealed class that represents different screens or
 * destinations in an app, with each screen identified by a unique route string.
 * The class has a primary constructor that takes a route string as a parameter,
 * which is assigned to the route property of the class.
 *
 * @param route - A string that represents the route of the screen.
 * This is a unique identifier that can be used to navigate to the specific screen within the app.
 *
 * @author Ilija Vucetic
 */
sealed class Screens(val route: String) {
    object SplashScreen : Screens("splash_screen")
    object LandingScreen : Screens("landing_screen")
    object LoginScreen : Screens("login_screen")
    object ForgotPasswordScreen : Screens("forgot_password_screen")
    object RegisterFirstScreen : Screens("register_first_screen")
    object RegisterSecondScreen : Screens("register_second_screen")
    object DogsNameScreen : Screens("dogs_name_screen")
    object VerifyEmailScreen : Screens("verify_email_screen")
    object ForgotPasswordRequestedScreen : Screens("forgot_password_requestedScreen")
    object ResetPasswordScreen : Screens("reset_password_screen")
}
