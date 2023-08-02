package com.bytecode.petsy.presentation.ui.screens.loginflow.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bytecode.petsy.R
import com.bytecode.petsy.data.model.dto.CountryDto
import com.bytecode.petsy.presentation.ui.commonui.AboutUsAndPrivacyView
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.commonui.countrypicker.CountryPickerBottomSheet
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.commonui.inputs.RoundedInput
import com.bytecode.petsy.presentation.ui.navigation.LoginFlowScreen

/**
 * Composable function that represents the register screen UI.
 *
 * @param navController The NavHostController used for navigation within the app.
 *
 * @author Ilija Vucetic
 */
@Composable
fun RegisterSecondScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel
) {
    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            PetsyImageBackground()
            HeaderOnboarding()
            Form(viewModel)
            BottomPart(navController, viewModel)
            CountryPickerDialog(viewModel)
        }
    }
}

@Composable
private fun BoxScope.BottomPart(navController: NavHostController, viewModel: RegisterViewModel) {
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigate(LoginFlowScreen.DogsNameScreen.route)
                }

                is ValidationEvent.Fail -> {}

                is ValidationEvent.UserExist -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GradientButton(
                text = stringResource(R.string.common_next),
                onClick = {
                    viewModel.onEvent(RegisterFormEvent.Submit(RegistrationStep.SECOND))
                }
            )
        }

        Spacer(modifier = Modifier.height(25.dp))
        AboutUsAndPrivacyView()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun BoxScope.Form(viewModel: RegisterViewModel) {
    val state = viewModel.state
    val context = LocalContext.current
    val kc = LocalSoftwareKeyboardController.current

    var firstName = remember { mutableStateOf(state.firstName) }
    var lastName = remember { mutableStateOf(state.lastName) }
    var countryName = remember { mutableStateOf(state.country) }

    LaunchedEffect(key1 = context) {
        viewModel.countryChangeEvents.collect { event ->
            when (event) {
                is CountryEvent.CountryChanged -> {
                    countryName.value = event.name
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 100.dp)
            .align(Alignment.TopCenter)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_temp_steps_second),
            contentDescription = "",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            style = MaterialTheme.typography.h1,
            text = stringResource(R.string.register_about_you),
            modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        RoundedInput(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            hint = stringResource(R.string.register_first_name),
            onValueChange = { viewModel.onEvent(RegisterFormEvent.FirstNameChanged(it)) },
            isError = state.firstNameError != null,
            errorMessage = state.firstNameError.toString(),
            textState = firstName
        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedInput(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            hint = stringResource(R.string.register_last_name),
            onValueChange = { viewModel.onEvent(RegisterFormEvent.LastNameChanged(it)) },
            isError = state.lastNameError != null,
            errorMessage = state.lastNameError.toString(),
            textState = lastName

        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedInput(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            hint = stringResource(R.string.register_country),
            endIcon = painterResource(id = R.drawable.ic_bottom_arrow_input_field),
            isEnabled = false,
            onClick = {
                kc?.hide()
                viewModel.onEvent(RegisterFormEvent.CountryFieldClicked())
            },
            isError = state.countryError != null,
            errorMessage = state.countryError.toString(),
            textState = countryName
        )

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun CountryPickerDialog(viewModel: RegisterViewModel) {
    Box {
        val context = LocalContext.current
        var expanded by remember { mutableStateOf(false) }
        var selectedCountry by remember { mutableStateOf<CountryDto?>(null) }
        val focusManager = LocalFocusManager.current



        LaunchedEffect(key1 = context) {
            viewModel.countryModalEvents.collect { event ->
                when (event) {
                    is ModalEvent.Open -> {
                        expanded = true
                    }

                    is ModalEvent.Close -> {
                        expanded = false
                    }
                }
            }
        }


        CountryPickerBottomSheet(
            title = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(R.string.search_country_title),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            expanded,
            onDismissRequest = {
                viewModel.onEvent(RegisterFormEvent.CloseCountryModal())
            },
            onItemSelected = {
                selectedCountry = it
                focusManager.clearFocus()
                viewModel.onEvent(RegisterFormEvent.CountryUpdated(it.name))
            }
        ) {
        }
    }
}