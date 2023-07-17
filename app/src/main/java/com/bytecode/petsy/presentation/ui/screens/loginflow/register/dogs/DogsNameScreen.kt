package com.bytecode.petsy.presentation.ui.screens.loginflow.register.dogs

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
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
import com.bytecode.petsy.presentation.ui.screens.loginflow.landing.LandingFlowEvent
import com.bytecode.petsy.presentation.ui.screens.loginflow.landing.rememberLifecycleEvent
import com.bytecode.petsy.presentation.ui.screens.loginflow.register.RegisterFormEvent
import com.bytecode.petsy.presentation.ui.screens.loginflow.register.RegisterViewModel
import com.bytecode.petsy.presentation.ui.screens.loginflow.register.RegistrationStep
import com.bytecode.petsy.presentation.ui.screens.loginflow.register.ValidationEvent
import kotlinx.coroutines.delay


@Composable
fun DogsNameScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel
) {
    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.onEvent(RegisterFormEvent.RefreshLanguage(""))
        }
    }

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
    val dogsListState = viewModel.dogsListFlow.collectAsState()
    val lazyListState = rememberLazyListState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(top = 150.dp)
            .align(Alignment.TopCenter)
            .fillMaxWidth()
    ) {
        val context = LocalContext.current
        val text = stringResource(R.string.the_dog_s_name_cannot_be_empty)

        LaunchedEffect(key1 = context) {


            viewModel.validationEvents.collect { event ->
                when (event) {
                    is ValidationEvent.Success -> {
                        delay(700)
                        launchPetsyActivity(context, viewModel)
//                        navController.navigate(LoginFlowScreen.VerifyEmailScreen.route)
                    }

                    is ValidationEvent.Fail -> {
                        Toast.makeText(
                            context,
                            text,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is ValidationEvent.UserExist -> {}
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
            items(items = dogsListState.value,
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
                                    dogsListState.value.indexOf(
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

        if (dogsListState.value.size < 3)
            IconTextButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 20.dp),
                onClick = { viewModel.onEvent(RegisterFormEvent.AddNewDogClicked()) }
            )
    }
}

private fun launchPetsyActivity(context: Context, viewModel: RegisterViewModel) {
    val intent = Intent(context, PetsyActivity::class.java)
    Log.d("Jezik", "sf: " + viewModel.selectedLanguageCode)
    intent.putExtra("LANG", viewModel.selectedLanguageCode)
    val activity = context.getActivity()
    activity?.startActivity(intent)
    activity?.finish()
}


//@Preview
//@Composable
//fun DogsNamePreview() {
//    DogsNameScreen(navController = rememberNavController())
//}