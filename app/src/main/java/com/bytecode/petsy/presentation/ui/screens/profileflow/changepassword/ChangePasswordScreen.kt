package com.bytecode.petsy.presentation.ui.screens.profileflow.changepassword

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import com.bytecode.petsy.presentation.ui.commonui.modals.PasswordChangedDialog
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowEvent
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
            ChangePasswordContent(viewModel, navController)
        }
    }
}

@Composable
fun ChangePasswordContent(viewModel: MainFlowViewModel, navController: NavHostController) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        var passwordOld = remember { mutableStateOf(viewModel.state.oldPassword) }
        var passwordNew = remember { mutableStateOf(viewModel.state.newPassword) }

        val passChanged by viewModel.passwordChanged.collectAsState()
        val showChangedPassDialog = remember { mutableStateOf(false) }

        if (passChanged) {
            PasswordChangedDialog(
                setShowDialog = {
                    showChangedPassDialog.value = it
                },
                onOkPressed = {
                    viewModel.onEvent(MainFlowEvent.ResetPasswordChangeDialogEvent(""))
                    navController.popBackStack()
                }
            )

        }

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
            text = stringResource(R.string.change_password),
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
                hint = stringResource(R.string.old_password),
                isPassword = true,
                onValueChange = {
                    viewModel.onEvent(MainFlowEvent.OldPasswordChanged(it))
                },
                isError = viewModel.state.oldPasswordError != null,
                errorMessage = viewModel.state.oldPasswordError.toString(),
                textState = passwordOld,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            RoundedInput(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                hint = stringResource(R.string.new_password_hint),
                isPassword = true,
                onValueChange = {
                    viewModel.onEvent(MainFlowEvent.NewPasswordChanged(it))
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

        Box(modifier = Modifier
            .constrainAs(saveButton) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bottomGuideline)
                width = androidx.constraintlayout.compose.Dimension.fillToConstraints
            }
            .padding(bottom = 30.dp)) {
            GradientButton(
                text = stringResource(R.string.btn_Save),
                onClick = {
                    viewModel.onEvent(MainFlowEvent.SavePasswordClicked(""))
                },
                alpha = if (viewModel.state.isPasswordChangeButtonEnabled) 1f else 0.3f,
                enabled = true //viewModel.state.isPasswordChangeButtonEnabled
            )
        }
    }
}