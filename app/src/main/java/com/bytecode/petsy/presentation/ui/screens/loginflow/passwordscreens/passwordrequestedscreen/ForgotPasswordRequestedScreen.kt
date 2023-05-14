package com.bytecode.petsy.presentation.ui.screens.loginflow.passwordscreens.passwordrequestedscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.commonui.AboutUsAndPrivacyView
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.navigation.LoginFlowScreen

@Composable
fun ForgotPasswordRequestedScreen(navController: NavHostController) {
    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            PetsyImageBackground()
            HeaderOnboarding()
            PasswordRequestedForm()
            PasswordRequestedBottomPart(navController)
        }
    }
}

@Composable
private fun BoxScope.PasswordRequestedBottomPart(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = 30.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GradientButton(
                text = stringResource(R.string.open_email),
                onClick = { navController.navigate(LoginFlowScreen.ResetPasswordScreen.route) }
            )
        }

        Spacer(modifier = Modifier.height(57.dp))
        AboutUsAndPrivacyView()
    }
}

@Composable
private fun BoxScope.PasswordRequestedForm() {
    Column(
        modifier = Modifier
            .padding(top = 150.dp)
            .align(Alignment.TopCenter)
            .fillMaxWidth()
    ) {
        Text(
            style = MaterialTheme.typography.h1,
            text = stringResource(R.string.reset_link_title),
            modifier = Modifier.padding(top = 34.dp, start = 20.dp, end = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            style = MaterialTheme.typography.h4,
            text = stringResource(R.string.reset_link_explanation),
            modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp)
        )
    }
}

