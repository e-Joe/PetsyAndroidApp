package com.bytecode.petsy.presentation.ui.navigation

import com.bytecode.petsy.R

sealed class BottomBarScreen(val route: String, val title: String, val icon: Int) {
    object DashboardScreen : BottomBarScreen(
        "dashboard_screen",
        title = "Home",
        icon = R.drawable.ic_bottom_nav_home
    )

    object BrushingScreen : BottomBarScreen(
        "brushing_screen",
        title = "Brushing",
        icon = R.drawable.ic_bottom_nav_toothbrush
    )

    object ProfileScreen : BottomBarScreen(
        "profile_screen",
        title = "Profile",
        icon = R.drawable.ic_bottom_nav_profile
    )
}
