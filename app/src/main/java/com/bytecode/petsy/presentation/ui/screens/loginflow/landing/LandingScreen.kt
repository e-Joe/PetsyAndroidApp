package com.bytecode.petsy.presentation.ui.screens.loginflow.landing

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.commonui.AboutUsAndPrivacyView
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.navigation.LoginFlowScreen
import com.bytecode.petsy.presentation.ui.theme.button_primary_text
import com.bytecode.petsy.presentation.ui.theme.h4bold
import com.bytecode.petsy.util.localeToEmoji
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
fun LandingScreen(navController: NavHostController, landingViewModel: LandingViewModel) {
    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(
        Color.White
    )

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            landingViewModel.onEvent(LandingFlowEvent.CheckLanguage(""))
        }
    }

    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            PetsyImageBackground()
            HeaderOnboarding()
            RenderLanguage(navController, landingViewModel)
            RenderTopPart()
            RenderBottomPart(navController)
        }
    }
}

@Composable
private fun RenderTopPart() {
    Text(
        text = stringResource(R.string.welcome_text),
        style = MaterialTheme.typography.h1,
        modifier = Modifier
            .padding(top = 150.dp, start = 20.dp, end = 20.dp)
    )
}

@Composable
private fun RenderLanguage(navController: NavHostController, viewModel: LandingViewModel) {
    Row(
        modifier = Modifier
            .padding(top = 80.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(LoginFlowScreen.ChangeLanguageScreen.route)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        val selectedLanguageCode by viewModel.selectedLanguageFlow.collectAsState()

        var languageName = stringResource(id = R.string.lang_english)

        when (selectedLanguageCode) {
            "GB" -> languageName = stringResource(id = R.string.lang_english)
            "DK" -> languageName = stringResource(R.string.lang_denmark)
            "RO" -> languageName = stringResource(R.string.lang_romanian)
            "RS" -> languageName = stringResource(R.string.lang_serbian)
        }

        Text(text = localeToEmoji(selectedLanguageCode), style = h4bold)

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = languageName,
            style = h4bold,
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_chevron_right_24),
            contentDescription = "Navigation Icon",
            modifier = Modifier.padding(top = 2.dp)
        )
    }

}

@Composable
private fun BoxScope.RenderBottomPart(navController: NavHostController) {
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
                onClick = {
                    navController.navigate(LoginFlowScreen.RegisterFirstScreen.route)
                }
            )

            TextButton(
                modifier = Modifier.height(50.dp),
                contentPadding = PaddingValues(0.dp),
                onClick = { navController.navigate(LoginFlowScreen.LoginScreen.route) }) {
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

@Composable
fun rememberLifecycleEvent(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current): Lifecycle.Event {
    var state by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            state = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    return state
}