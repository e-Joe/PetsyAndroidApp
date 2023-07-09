package com.bytecode.petsy.presentation.ui.screens.loginflow.passwordscreens.resetpassword

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.commonui.AboutUsAndPrivacyView
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.commonui.custom.PasswordRules
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.commonui.inputs.RoundedInput
import com.bytecode.petsy.presentation.ui.navigation.LoginFlowScreen

@Composable
fun ResetPasswordScreen(navController: NavHostController, viewModel: ResetPasswordViewModel) {
    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            PetsyImageBackground()
            HeaderOnboarding()
            PasswordResetForm(viewModel)
            PasswordRequestedBottomPart(navController, viewModel)
        }
    }
}

@Composable
private fun BoxScope.PasswordRequestedBottomPart(
    navController: NavHostController,
    viewModel: ResetPasswordViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = 30.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GradientButton(
                text = stringResource(R.string.btn_change_password),
                onClick = {
                    navController.navigate(LoginFlowScreen.LandingScreen.route) {
                        popUpTo(navController.graph.id){
                            inclusive = true
                        }
                    }
                },
                alpha = if (viewModel.state.isPasswordValid) 1f else 0.3f,
                enabled = viewModel.state.isPasswordValid
            )
        }

        Spacer(modifier = Modifier.height(57.dp))
        AboutUsAndPrivacyView()
    }
}

@Composable
private fun BoxScope.PasswordResetForm(viewModel: ResetPasswordViewModel) {

    val state = viewModel.state
    val context = LocalContext.current

    var password = remember { mutableStateOf(state.password) }

    Column(
        modifier = Modifier
            .padding(top = 150.dp)
            .align(Alignment.TopCenter)
            .fillMaxWidth()
    ) {
        Text(
            style = MaterialTheme.typography.h1,
            text = stringResource(R.string.reset_your_password_title),
            modifier = Modifier.padding(top = 34.dp, start = 20.dp, end = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            style = MaterialTheme.typography.h4,
            text = stringResource(R.string.reset_your_password_explanation),
            modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedInput(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            hint = stringResource(R.string.new_password_hint),
            isPassword = true,
            onValueChange = {
                viewModel.onEvent(ResetPasswordEvent.PasswordChanged(it))
            },
            isError = state.passwordError != null,
            errorMessage = state.passwordError.toString(),
            textState = password,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        PasswordRules(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(top = 20.dp),
            isLengthRuleValid = viewModel.state.isPasswordLength,
            isUpperCaseRuleValid = viewModel.state.isPasswordUpperCaseValid,
            isLoweCaseRuleValid = viewModel.state.isPasswordLowerCaseValid,
            isDigitRuleValid = viewModel.state.isPasswordDigitValid,
        )
    }
}