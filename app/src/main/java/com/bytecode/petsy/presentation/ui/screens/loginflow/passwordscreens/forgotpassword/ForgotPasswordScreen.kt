package com.bytecode.petsy.presentation.ui.screens.loginflow.passwordscreens.forgotpassword

import androidx.compose.foundation.layout.*
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
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.commonui.inputs.RoundedInput
import com.bytecode.petsy.presentation.ui.navigation.LoginFlowScreen

@Composable
fun ForgotPasswordScreen(navController: NavHostController, viewModel: ForgotPasswordViewModel) {
    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            PetsyImageBackground()
            HeaderOnboarding()
            PasswordRequestedForm(viewModel)
            PasswordRequestedBottomPart(navController,viewModel)
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

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GradientButton(
                text = stringResource(R.string.common_send),
                onClick = { navController.navigate(LoginFlowScreen.ForgotPasswordRequestedScreen.route) },
                alpha = if (viewModel.state.isEmailValid) 1f else 0.3f,
                enabled = viewModel.state.isEmailValid
            )
        }

        Spacer(modifier = Modifier.height(57.dp))
        AboutUsAndPrivacyView()
    }
}

@Composable
private fun BoxScope.PasswordRequestedForm(viewModel: ForgotPasswordViewModel) {
    val state = viewModel.state
    val context = LocalContext.current

    var email = remember { mutableStateOf(state.email) }

    Column(
        modifier = Modifier
            .padding(top = 150.dp)
            .align(Alignment.TopCenter)
            .fillMaxWidth()
    ) {
        Text(
            style = MaterialTheme.typography.h1,
            text = stringResource(R.string.forgot_your_password_title),
            modifier = Modifier.padding(top = 34.dp, start = 20.dp, end = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        RoundedInput(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            hint = stringResource(R.string.common_email),
            onValueChange = {
                viewModel.onEvent(ForgotPasswordEvent.EmailChanged(it))
            },
            isError = state.emailError != null,
            errorMessage = state.emailError.toString(),
            textState = email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            requestFocus = true
        )
    }
}


//@Preview
//@Composable
//fun ForgotPasswordPreview() {
//    ForgotPasswordScreen(rememberNavController())
//}