package com.bytecode.petsy.ui.screens.loginflow.landing

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bytecode.petsy.R
import com.bytecode.petsy.ui.commonui.AboutUsAndPrivacyView
import com.bytecode.petsy.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.ui.navigation.Screens
import com.bytecode.petsy.ui.theme.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * he @Composable function LandingScreen is a function in a Jetpack Compose-based Android
 * application that represents the UI for the landing screen of the app. It takes a
 * NavHostController as a parameter, which is used for navigation within the app.
 *
 *  * @param navController The NavHostController used for navigation within the app.
 *
 * @author Ilija Vucetic
 */
@Composable
fun LandingScreen(navController: NavHostController) {
    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(
        Color.White
    )

    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            PetsyImageBackground()
            HeaderOnboarding()
            RenderTopPart()
            RenderBottomPart(navController)
        }
    }
}

@Composable
private fun RenderTopPart() {
    Text(
        text = "Welcome to\n" + "smart toothbrush ! \uD83D\uDC3E",
        style = MaterialTheme.typography.h1,
        modifier = Modifier
            .padding(top = 150.dp, start = 20.dp, end = 20.dp)
    )
}

@Composable
private fun BoxScope.RenderBottomPart(navController: NavHostController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GradientButton(
                text = stringResource(R.string.common_register),
                onClick = { navController.navigate(Screens.RegisterFirstScreen.route) }
            )

            TextButton(
                modifier = Modifier.height(50.dp),
                contentPadding = PaddingValues(0.dp),
                onClick = { navController.navigate(Screens.LoginScreen.route) }) {
                Text(
                    text = stringResource(R.string.common_login),
                    style = button_primary_text
                )
            }
        }

        Spacer(modifier = Modifier.height(57.dp))
        AboutUsAndPrivacyView()
    }
}

@Preview
@Composable
fun LandingPreview() {
    LandingScreen(rememberNavController())
}