package com.bytecode.petsy.ui.screens.loginflow.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bytecode.petsy.R
import com.bytecode.petsy.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.ui.theme.StatusBarColorLight
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Creates a Composable function that displays a splash screen with an image logo.
 *
 * @param navController The NavHostController used for navigation.
 *
 * @author Ilija Vucetic
 */
@Composable
fun SplashScreen(navController: NavHostController) {
    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(
        StatusBarColorLight
    )

    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            PetsyImageBackground()
            Image(
                painter = painterResource(id = R.drawable.img_logo),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 334.dp)
            )
        }
    }
}