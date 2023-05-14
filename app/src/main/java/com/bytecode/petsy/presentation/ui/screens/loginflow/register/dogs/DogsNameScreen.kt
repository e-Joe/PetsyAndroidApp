package com.bytecode.petsy.presentation.ui.screens.loginflow.register.dogs

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.commonui.AboutUsAndPrivacyView
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.buttons.IconTextButton
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.commonui.inputs.RoundedInput
import com.bytecode.petsy.presentation.ui.navigation.LoginFlowScreen
import com.bytecode.petsy.presentation.ui.screens.loginflow.register.RegisterFormEvent
import com.bytecode.petsy.presentation.ui.screens.loginflow.register.RegisterViewModel
import com.bytecode.petsy.presentation.ui.screens.loginflow.register.RegistrationStep
import com.bytecode.petsy.presentation.ui.screens.loginflow.register.ValidationEvent


@Composable
fun DogsNameScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel
) {
    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            PetsyImageBackground()
            HeaderOnboarding()
            DogsList(viewModel, navController)
            BottomPart(viewModel)
        }
    }
}

@Composable
private fun BoxScope.BottomPart(viewModel: RegisterViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = 30.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            GradientButton(
                text = stringResource(R.string.common_next),
                onClick = { viewModel.onEvent(RegisterFormEvent.Submit(RegistrationStep.THIRD)) }
            )
        }

        Spacer(modifier = Modifier.height(57.dp))
        AboutUsAndPrivacyView()
    }
}

@Composable
private fun BoxScope.DogsList(viewModel: RegisterViewModel, navController: NavHostController) {
    val todoListState = viewModel.dogsListFlow.collectAsState()
    val lazyListState = rememberLazyListState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(top = 150.dp)
            .align(Alignment.TopCenter)
            .fillMaxWidth()
    ) {
        val context = LocalContext.current

        LaunchedEffect(key1 = context) {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    is ValidationEvent.Success -> {
                        navController.navigate(LoginFlowScreen.VerifyEmailScreen.route)
                    }

                    is ValidationEvent.Fail -> {
                        Toast.makeText(
                            context,
                            "The dog's name cannot be empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.img_temp_steps_third),
            contentDescription = "",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            style = MaterialTheme.typography.h1,
            text = stringResource(R.string.register_your_dogs),
            modifier = Modifier.padding(top = 34.dp, start = 20.dp, end = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth(), state = lazyListState) {
            items(items = todoListState.value,
                itemContent = { dog ->
                    var name = remember { mutableStateOf(dog.name) }
                    RoundedInput(
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                        hint = stringResource(R.string.register_your_dogs_name),
                        isEnabled = true,
                        textState = name,
                        onValueChange = {
                            viewModel.onEvent(
                                RegisterFormEvent.OnDogNameChanged(
                                    todoListState.value.indexOf(
                                        dog
                                    ), it
                                )
                            )
                        },
                        isLabelEnabled = true,
                        requestFocus = true
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (todoListState.value.size < 3)
            IconTextButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 20.dp),
                onClick = { viewModel.onEvent(RegisterFormEvent.AddNewDogClicked()) }
            )
    }
}


//@Preview
//@Composable
//fun DogsNamePreview() {
//    DogsNameScreen(navController = rememberNavController())
//}