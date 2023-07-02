package com.bytecode.petsy.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowViewModel
import com.bytecode.petsy.presentation.ui.screens.mainflow.brushing.BrushingScreen
import com.bytecode.petsy.presentation.ui.screens.mainflow.dashboard.DashboardScreen
import com.bytecode.petsy.presentation.ui.screens.mainflow.profile.ProfileScreen
import com.bytecode.petsy.presentation.ui.screens.profileflow.changepassword.ChangePasswordScreen
import com.bytecode.petsy.presentation.ui.screens.profileflow.deleteaccount.info.DeleteAccountInfoScreen
import com.bytecode.petsy.presentation.ui.screens.profileflow.deleteaccount.password.DeleteAccountPasswordScreen
import com.bytecode.petsy.presentation.ui.screens.profileflow.deleteaccount.reason.DeleteAccountReasonScreen
import com.bytecode.petsy.presentation.ui.screens.profileflow.mypets.MyPetsProfileScreen
import com.bytecode.petsy.presentation.ui.screens.profileflow.tutorials.TutorialsScreen

@Composable
fun MainFlowNavGraph(navController: NavHostController, mainFlowViewModel: MainFlowViewModel) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.DashboardScreen.route
    ) {

        composable(route = BottomBarScreen.DashboardScreen.route) {
            DashboardScreen(mainFlowViewModel, navController)
        }
        composable(route = BottomBarScreen.BrushingScreen.route) {
            BrushingScreen(mainFlowViewModel, navController)
        }
        composable(route = BottomBarScreen.ProfileScreen.route) {
            ProfileScreen(mainFlowViewModel, navController)
        }

        profileNavGraph(navController = navController, mainFlowViewModel)

        deleteAccNavGraph(navController = navController, mainFlowViewModel)
    }
}

fun NavGraphBuilder.profileNavGraph(
    navController: NavHostController,
    mainFlowViewModel: MainFlowViewModel
) {
    navigation(
        route = "profileScreens",
        startDestination = ProfileScreenNav.MyPets.route
    ) {
        composable(route = ProfileScreenNav.MyPets.route) {
            MyPetsProfileScreen(viewModel = mainFlowViewModel)
        }
        composable(route = ProfileScreenNav.Tutorials.route) {
            TutorialsScreen(viewModel = mainFlowViewModel)
        }
        composable(route = ProfileScreenNav.ChangePassword.route) {
            ChangePasswordScreen(viewModel = mainFlowViewModel)
        }

        composable(route = ProfileScreenNav.Privacy.route) {
            ScreenContent(name = ProfileScreenNav.Privacy.route) {
                navController.popBackStack(
                    route = ProfileScreenNav.Privacy.route,
                    inclusive = false
                )
            }
        }
    }
}

fun NavGraphBuilder.deleteAccNavGraph(
    navController: NavHostController,
    mainFlowViewModel: MainFlowViewModel
) {
    navigation(
        route = "deleteAccScreens",
        startDestination = DeleteScreenNav.DeleteAccReasonScreen.route
    ) {
        composable(route = DeleteScreenNav.DeleteAccReasonScreen.route) {
            DeleteAccountReasonScreen(viewModel = mainFlowViewModel)
        }
        composable(route = DeleteScreenNav.DeleteAccInfoScreen.route) {
            DeleteAccountInfoScreen(viewModel = mainFlowViewModel)
        }
        composable(route = DeleteScreenNav.DeleteAccPasswordScreen.route) {
            DeleteAccountPasswordScreen(viewModel = mainFlowViewModel)
        }
    }
}

sealed class ProfileScreenNav(val route: String) {
    object MyPets : ProfileScreenNav(route = "MY_PETS")
    object Tutorials : ProfileScreenNav(route = "TUTORIALS")
    object ChangePassword : ProfileScreenNav(route = "CHANGE_PASSWORD")
    object Privacy : ProfileScreenNav(route = "PRIVACY")
    object DeleteAccount : ProfileScreenNav(route = "DELETE_ACC")
}

sealed class DeleteScreenNav(val route: String) {
    object DeleteAccReasonScreen : DeleteScreenNav(route = "DELETE__ACC_REASON")
    object DeleteAccInfoScreen : DeleteScreenNav(route = "DELETE__ACC_INFO")
    object DeleteAccPasswordScreen : DeleteScreenNav(route = "DELETE__ACC_PASS")

}