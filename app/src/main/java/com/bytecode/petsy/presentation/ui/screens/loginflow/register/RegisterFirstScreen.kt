package com.bytecode.petsy.presentation.ui.screens.loginflow.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
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
import com.bytecode.petsy.presentation.ui.theme.h4_link


/**
 * Composable function that represents the register screen UI.
 *
 * @param navController The NavHostController used for navigation within the app.
 *
 * @author Ilija Vucetic
 */
@Composable
fun RegisterFirstScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel,
) {
    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            PetsyImageBackground()
            HeaderOnboarding()
            Form(navController, viewModel)
            BottomPart(navController, viewModel)
            CountryPickerDialog(viewModel)
        }
    }
}


@Composable
private fun BoxScope.BottomPart(navController: NavHostController, viewModel: RegisterViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = 30.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GradientButton(
                text = stringResource(R.string.common_next),
                onClick = {
                    viewModel.onEvent(RegisterFormEvent.Submit())
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.register_already_have_account),
                    style = MaterialTheme.typography.h4
                )
                Text(
                    text = stringResource(R.string.register_login_here),
                    style = h4_link,
                    modifier = Modifier
                        .padding(start = 3.dp)
                        .clickable(
                            true,
                            onClick = {
                                navController.popBackStack()
                                navController.navigate(LoginFlowScreen.LoginScreen.route)
                            }),
                    textDecoration = TextDecoration.Underline
                )
            }
        }

        Spacer(modifier = Modifier.height(57.dp))
        AboutUsAndPrivacyView()
    }
}

@Composable
private fun BoxScope.Form(navController: NavHostController, viewModel: RegisterViewModel) {
    val state = viewModel.state
    val context = LocalContext.current

    var email = remember { mutableStateOf(state.email) }
    var password = remember { mutableStateOf(state.password) }

    Column(
        modifier = Modifier
            .padding(top = 150.dp)
            .align(Alignment.TopCenter)
            .fillMaxWidth()
    ) {

        val text = stringResource(R.string.user_already_exist)

        LaunchedEffect(key1 = context) {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    is ValidationEvent.Success -> {
                        navController.navigate(LoginFlowScreen.RegisterSecondScreen.route)
                    }

                    is ValidationEvent.Fail -> {}

                    is ValidationEvent.UserExist -> {
                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.img_temp_steps),
            contentDescription = "",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            style = MaterialTheme.typography.h1,
            text = stringResource(R.string.register_your_account),
            modifier = Modifier.padding(top = 34.dp, start = 20.dp, end = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        RoundedInput(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            hint = stringResource(R.string.common_email),
            onValueChange = {
                viewModel.onEvent(RegisterFormEvent.EmailChanged(it))
            },
            isError = state.emailError != null,
            errorMessage = state.emailError.toString(),
            textState = email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedInput(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            hint = stringResource(R.string.common_password),
            isPassword = true,
            onValueChange = {
                viewModel.onEvent(RegisterFormEvent.PasswordChanged(it))
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

