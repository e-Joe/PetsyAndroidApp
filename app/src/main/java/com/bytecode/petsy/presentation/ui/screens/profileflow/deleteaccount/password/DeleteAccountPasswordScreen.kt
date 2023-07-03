package com.bytecode.petsy.presentation.ui.screens.profileflow.deleteaccount.password

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bytecode.framework.extension.getActivity
import com.bytecode.framework.extension.launchActivity
import com.bytecode.petsy.presentation.ui.activities.welcome.WelcomeActivity
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.commonui.inputs.RoundedInput
import com.bytecode.petsy.presentation.ui.commonui.modals.AccountDeletedDialog
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowEvent
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowViewModel

@Composable
fun DeleteAccountPasswordScreen(
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
            DeleteAccountContent(navController, viewModel)
        }
    }
}

@Composable
fun DeleteAccountContent(navController: NavHostController, viewModel: MainFlowViewModel) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val context = LocalContext.current
        var password = remember { mutableStateOf("") }
        val accountDeleted by viewModel.accountDeleted.collectAsState()
        val showChangedPassDialog = remember { mutableStateOf(false) }

        val (
            titleText,
            mainPart,
            deleteProfileBtn
        ) = createRefs()

        val topGuideline = createGuidelineFromTop(110.dp)
        val bottomGuideline = createGuidelineFromBottom(20.dp)

        if (accountDeleted) {
            AccountDeletedDialog(
                setShowDialog = {
                    showChangedPassDialog.value = it
                },
                onOkPressed = {
                    context.launchActivity<WelcomeActivity> { }
                    val activity = context.getActivity()
                    activity?.finish()
                }
            )

        }

        Text(
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(topGuideline)
                    start.linkTo(parent.start)
                }
                .padding(horizontal = 20.dp),
            text = "Account",
            style = MaterialTheme.typography.h1
        )

        Column(
            modifier = Modifier
                .constrainAs(mainPart) {
                    top.linkTo(titleText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(deleteProfileBtn.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .padding(horizontal = 20.dp)
                .padding(top = 30.dp)
        ) {
            RoundedInput(
                modifier = Modifier.padding(start = 0.dp, end = 0.dp),
                hint = "Password",
                isPassword = true,
                onValueChange = {
                    viewModel.onEvent(MainFlowEvent.DeletePasswordChanged(it))
                },
                isError = viewModel.state.deletePasswordError != null,
                errorMessage = viewModel.state.deletePasswordError.toString(),
                textState = password,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

        }

        GradientButton(
            modifier = Modifier
                .constrainAs(deleteProfileBtn) {
                    bottom.linkTo(bottomGuideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(70.dp),
            text = "Delete profile",
            onClick = {
                viewModel.onEvent(MainFlowEvent.DeleteUserClicked(""))
            }
        )
    }
}