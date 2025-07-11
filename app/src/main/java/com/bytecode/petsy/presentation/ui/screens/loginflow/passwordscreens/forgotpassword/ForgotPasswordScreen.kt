package com.bytecode.petsy.presentation.ui.screens.loginflow.passwordscreens.forgotpassword

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.bytecode.petsy.presentation.ui.commonui.modals.PasswordChangedDialog


@Composable
fun ForgotPasswordScreen(navController: NavHostController, viewModel: ForgotPasswordViewModel) {
    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            PetsyImageBackground()
            HeaderOnboarding()
            PasswordRequestedForm(viewModel)
            PasswordRequestedBottomPart(navController, viewModel)
        }
    }
}

@Composable
private fun BoxScope.PasswordRequestedBottomPart(
    navController: NavHostController,
    viewModel: ForgotPasswordViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = 30.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val showChangedPassDialog = remember { mutableStateOf(false) }

        val state = viewModel.stateFlow.collectAsState()

        if (state.value.showPassDialog) {
            PasswordChangedDialog(
                setShowDialog = {
                    showChangedPassDialog.value = it
                },
                onOkPressed = {
                    navController.popBackStack()
                }
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GradientButton(
                text = stringResource(R.string.common_send),
                onClick = { viewModel.onEvent(ForgotPasswordEvent.Submit("")) },
                alpha = if (viewModel.state.isPasswordChangeButtonEnabled) 1f else 0.3f,
                enabled = viewModel.state.isEmailValid
            )
        }

        Spacer(modifier = Modifier.height(57.dp))
        AboutUsAndPrivacyView()
    }
}

@Composable
private fun BoxScope.PasswordRequestedForm(viewModel: ForgotPasswordViewModel) {

    var email = remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .padding(top = 120.dp)
            .align(Alignment.TopCenter)
            .fillMaxWidth()
    ) {
        var passwordNew = remember { mutableStateOf(viewModel.state.password) }

        val state = viewModel.stateFlow.collectAsState()

        Text(
            style = MaterialTheme.typography.h1,
            text = stringResource(R.string.login_forgot_password),
            modifier = Modifier.padding(top = 34.dp, start = 20.dp, end = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        RoundedInput(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            hint = stringResource(R.string.common_email),
            onValueChange = {
                viewModel.onEvent(ForgotPasswordEvent.EmailChanged(it))
            },
            isError = state.value.emailError.isNotEmpty(),
            errorMessage = state.value.emailError,
            textState = email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            requestFocus = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedInput(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            hint = stringResource(R.string.new_password_hint),
            isPassword = true,
            onValueChange = {
                viewModel.onEvent(ForgotPasswordEvent.PasswordChanged(it))
            },
            isError = false,
            errorMessage = "",
            textState = passwordNew,
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


//@Preview
//@Composable
//fun ForgotPasswordPreview() {
//    ForgotPasswordScreen(rememberNavController())
//}