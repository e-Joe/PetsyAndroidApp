package com.bytecode.petsy.presentation.ui.screens.mainflow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bytecode.petsy.presentation.ui.navigation.BottomBarScreen
import com.bytecode.petsy.presentation.ui.navigation.MainFlowNavGraph
import com.bytecode.petsy.presentation.ui.theme.NavigationItemActiveColor

@Composable
fun MainFlowScreen(navController: NavHostController = rememberNavController()) {

    var mainFlowViewModel: MainFlowViewModel = viewModel()

    Scaffold(bottomBar = { BottomBar(navController = navController, mainFlowViewModel) }) {
        it
        MainFlowNavGraph(navController, mainFlowViewModel)
    }
}

@Composable
fun BottomBar(navController: NavHostController, mainFlowViewModel: MainFlowViewModel) {
    val screens = listOf(
        BottomBarScreen.DashboardScreen,
        BottomBarScreen.BrushingScreen,
        BottomBarScreen.ProfileScreen,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = Color.White,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                .height(85.dp)
                .graphicsLayer {
                    shape = RoundedCornerShape(20.dp)
                    clip = true
                    shadowElevation = 10f
                }
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController,
                    mainFlowViewModel
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    viewModel: MainFlowViewModel
) {
    val isSelected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true

    var unSelectedColor = Color.Black
    if (viewModel.state.brushingPhase != BrushingState.NOT_STARTED)
        unSelectedColor = Color.Black.copy(alpha = 0.5f)

    BottomNavigationItem(
        modifier = Modifier
            .height(85.dp),
        icon = {
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(if (isSelected) NavigationItemActiveColor else Color.Transparent)
            ) {
                Icon(
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                        .align(Alignment.Center),
                    imageVector = ImageVector.vectorResource(id = screen.icon),
                    contentDescription = "Navigation Icon"
                )

                Box(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .width(4.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .align(Alignment.BottomCenter)
                        .background(Color.White)
                )
            }
        },
        selected = isSelected,
        selectedContentColor = Color.White,
        unselectedContentColor = unSelectedColor,
        onClick = {
            if (viewModel.state.brushingPhase == BrushingState.NOT_STARTED)
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
        }
    )
}
