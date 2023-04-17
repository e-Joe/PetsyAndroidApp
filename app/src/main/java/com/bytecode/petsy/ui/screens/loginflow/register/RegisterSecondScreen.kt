package com.bytecode.petsy.ui.screens.loginflow.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bytecode.petsy.R
import com.bytecode.petsy.ui.commonui.AboutUsAndPrivacyView
import com.bytecode.petsy.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.ui.commonui.inputs.RoundedInput

/**
 * Composable function that represents the register screen UI.
 *
 * @param navController The NavHostController used for navigation within the app.
 *
 * @author Ilija Vucetic
 */
@Composable
fun RegisterSecondScreen(navController: NavHostController) {
    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            PetsyImageBackground()
            HeaderOnboarding()
            RegisterForm()
            RegisterScreenBottomPart(navController)
        }
    }
}

@Composable
private fun BoxScope.RegisterScreenBottomPart(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = 30.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            GradientButton(
                text = stringResource(R.string.common_next),
                onClick = {}
            )
        }

        Spacer(modifier = Modifier.height(80.dp))
        AboutUsAndPrivacyView()
    }
}

@Composable
private fun BoxScope.RegisterForm() {
    Column(
        modifier = Modifier
            .padding(top = 150.dp)
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
            modifier = Modifier.padding(top = 34.dp, start = 20.dp, end = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        RoundedInput(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            hint = stringResource(R.string.register_first_name)
        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedInput(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            hint = stringResource(R.string.register_last_name)
        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedInput(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            hint = stringResource(R.string.register_country),
            endIcon = painterResource(id = R.drawable.ic_bottom_arrow_input_field),
            isEnabled = false
        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedInput(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            hint = stringResource(R.string.register_phone_number)
        )
    }
}


@Preview
@Composable
fun RegisterSecondPreview() {
    RegisterSecondScreen(navController = rememberNavController())
}