package com.bytecode.petsy.presentation.ui.screens.profileflow.changepassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.commonui.custom.PasswordRules
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.commonui.inputs.RoundedInput
import com.bytecode.petsy.presentation.ui.screens.loginflow.register.RegisterFormEvent
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowViewModel

@Composable
fun ChangePasswordScreen(
    viewModel: MainFlowViewModel,
    navController: NavHostController = rememberNavController()
) {

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            PetsyImageBackground()
            HeaderOnboarding()
            ChangePasswordContent()
        }
    }
}

@Composable
fun ChangePasswordContent() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        var password = remember { mutableStateOf("") }

        val (
            titleText,
            mainPart,
            saveButton,
        ) = createRefs()

        val topGuideline = createGuidelineFromTop(110.dp)
        val bottomGuideline = createGuidelineFromBottom(20.dp)

        Text(
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(topGuideline)
                    start.linkTo(parent.start)
                }
                .padding(horizontal = 20.dp),
            text = "Change password",
            style = MaterialTheme.typography.h1
        )

        Column(
            modifier = Modifier
                .constrainAs(mainPart) {
                    top.linkTo(titleText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(saveButton.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .padding(top = 30.dp)
        ) {

            RoundedInput(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                hint = stringResource(R.string.common_password),
                isPassword = true,
                onValueChange = {
//                    viewModel.onEvent(RegisterFormEvent.PasswordChanged(it))
                },
//                isError = state.passwordError != null,
//                errorMessage = state.passwordError.toString(),
                textState = password,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            RoundedInput(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                hint = stringResource(R.string.common_password),
                isPassword = true,
                onValueChange = {
//                    viewModel.onEvent(RegisterFormEvent.PasswordChanged(it))
                },
//                isError = state.passwordError != null,
//                errorMessage = state.passwordError.toString(),
                textState = password,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            PasswordRules(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .padding(top = 20.dp),
//                isLengthRuleValid = viewModel.state.isPasswordLength,
//                isUpperCaseRuleValid = viewModel.state.isPasswordUpperCaseValid,
//                isLoweCaseRuleValid = viewModel.state.isPasswordLowerCaseValid,
//                isDigitRuleValid = viewModel.state.isPasswordDigitValid,
            )
        }

        Box(modifier = Modifier
            .constrainAs(saveButton) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bottomGuideline)
                width = androidx.constraintlayout.compose.Dimension.fillToConstraints
            }
            .padding(bottom = 30.dp)) {
            GradientButton(
                text = "Save",
                onClick = {

                }
            )
        }
    }
}