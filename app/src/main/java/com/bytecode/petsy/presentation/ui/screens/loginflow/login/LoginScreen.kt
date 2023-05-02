package com.bytecode.petsy.presentation.ui.screens.loginflow.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bytecode.framework.extension.getActivity
import com.bytecode.framework.extension.launchActivity
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.activities.petsy.PetsyActivity
import com.bytecode.petsy.presentation.ui.commonui.AboutUsAndPrivacyView
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.commonui.buttons.IconTextButton
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.commonui.inputs.RoundedInput
import com.bytecode.petsy.presentation.ui.navigation.Screens
import com.bytecode.petsy.presentation.ui.theme.h4_link
import kotlinx.coroutines.flow.collect

/**
 * Composable function that represents the register screen UI.
 *
 * @param navController The NavHostController used for navigation within the app.
 *
 * @author Ilija Vucetic
 */
@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            PetsyImageBackground()
            HeaderOnboarding()
            LoginForm(navController, viewModel)
            LoginScreenBottomPart(navController, viewModel)
        }
    }
}

@Composable
private fun BoxScope.LoginScreenBottomPart(
    navController: NavHostController,
    viewModel: LoginViewModel
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = 30.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            GradientButton(
                text = stringResource(R.string.login_login),
                onClick = {
                    viewModel.onEvent(LoginFormEvent.Submit())
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.login_dont_have_account),
                    style = MaterialTheme.typography.h4
                )
                Text(
                    text = stringResource(R.string.login_register_here),
                    style = h4_link,
                    modifier = Modifier
                        .padding(start = 3.dp)
                        .clickable(enabled = true) {
                            navController.popBackStack()
                            navController.navigate(Screens.RegisterFirstScreen.route)
                        },
                    textDecoration = TextDecoration.Underline,

                    )
            }
        }
        Spacer(modifier = Modifier.height(57.dp))
        AboutUsAndPrivacyView()
    }
}

@Composable
private fun BoxScope.LoginForm(navController: NavHostController, viewModel: LoginViewModel) {
    Column(
        modifier = Modifier
            .padding(top = 150.dp)
            .align(Alignment.TopCenter)
            .fillMaxWidth()
    ) {
        val state = viewModel.state
        val context = LocalContext.current

        LaunchedEffect(key1 = context) {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    is LoginViewModel.ValidationEvent.Success -> {
                        launchPetsyActivity(context)
                    }
                }
            }
        }

        Text(
            style = MaterialTheme.typography.h1,
            text = stringResource(R.string.login_login),
            modifier = Modifier.padding(top = 34.dp, start = 20.dp, end = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        RoundedInput(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            hint = stringResource(R.string.common_email),
            isPassword = false,
            onValueChange = { viewModel.onEvent(LoginFormEvent.EmailChanged(it)) },
            isError = state.emailError != null
        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedInput(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            hint = stringResource(R.string.common_password),
            isPassword = true,
            onValueChange = { viewModel.onEvent(LoginFormEvent.PasswordChanged(it)) },
            isError = state.passwordError != null
        )

        IconTextButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 20.dp, top = 20.dp),
            showIcon = false,
            text = stringResource(R.string.login_forgot_password),
            onClick = { navController.navigate(Screens.ForgotPasswordScreen.route) }
        )
    }
}

private fun launchPetsyActivity(context: Context) {
    context.launchActivity<PetsyActivity> { }
    val activity = context.getActivity()
    activity?.finish()
}


@Preview
@Composable
fun RegisterPreview() {
    LoginScreen(rememberNavController())
}