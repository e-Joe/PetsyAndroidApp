package com.bytecode.petsy.presentation.ui.screens.mainflow.profile

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bytecode.framework.extension.getActivity
import com.bytecode.framework.extension.launchActivity
import com.bytecode.petsy.R
import com.bytecode.petsy.data.model.dto.user.UserDto
import com.bytecode.petsy.presentation.ui.activities.welcome.WelcomeActivity
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.commonui.modals.LogOutDialog
import com.bytecode.petsy.presentation.ui.navigation.DeleteScreenNav
import com.bytecode.petsy.presentation.ui.navigation.ProfileScreenNav
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowEvent
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowViewModel
import com.bytecode.petsy.presentation.ui.theme.ScreenBackgroundColor
import com.bytecode.petsy.presentation.ui.theme.h4bold
import com.bytecode.petsy.util.LocaleUtils

@Composable
fun ProfileScreen(
    viewModel: MainFlowViewModel,
    navController: NavHostController = rememberNavController()
) {

    val userState = viewModel.userFLow.collectAsState(UserDto())

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {

            PetsyImageBackground()
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 130.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val context = LocalContext.current
                Spacer(modifier = Modifier.height(100.dp))

                Text(
                    text = userState.value.firstName + " " + userState.value.lastName,
                    style = MaterialTheme.typography.h1,
                )

                Text(
                    text = userState.value.email,
                    style = MaterialTheme.typography.h4,
                )

                Spacer(modifier = Modifier.height(70.dp))

                ProfileOptionCard(
                    isVisibleRightIcon = true,
                    stringResource(R.string.my_pets),
                    ImageVector.vectorResource(id = R.drawable.ic_profile_my_pets)
                ) {
                    navController.navigate(ProfileScreenNav.MyPets.route)

                }

                Spacer(modifier = Modifier.height(10.dp))

                ProfileOptionCard(
                    isVisibleRightIcon = true,
                    stringResource(R.string.tutorials_education),
                    ImageVector.vectorResource(id = R.drawable.ic_profile_tutorials_education),
                ) {
                    navController.navigate(ProfileScreenNav.Tutorials.route)
                }

                Spacer(modifier = Modifier.height(10.dp))

                ProfileOptionCard(
                    isVisibleRightIcon = true,
                    stringResource(R.string.change_password),
                    ImageVector.vectorResource(id = R.drawable.ic_profile_change_password),
                )
                {
                    navController.navigate(ProfileScreenNav.ChangePassword.route)
                }

                Spacer(modifier = Modifier.height(10.dp))

                ProfileOptionCard(
                    isVisibleRightIcon = true,
                    stringResource(id = R.string.change_language_title),
                    ImageVector.vectorResource(id = R.drawable.ic_language),
                )
                {
//                    LocaleUtils.setLocale(context)
                    navController.navigate(ProfileScreenNav.ChangeLanguage.route)
                }

                Spacer(modifier = Modifier.height(10.dp))

                val privacyLink =
                    remember {
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://petsie.pet/terms-of-use/")
                        )
                    }

                ProfileOptionCard(
                    isVisibleRightIcon = true,
                    stringResource(R.string.privacy),
                    ImageVector.vectorResource(id = R.drawable.ic_profile_privacy),
                ) {
                    context.startActivity(privacyLink)
                }

                Spacer(modifier = Modifier.height(10.dp))

                ProfileOptionCard(
                    isVisibleRightIcon = true,
                    stringResource(R.string.delete_account),
                    ImageVector.vectorResource(id = R.drawable.ic_profile_delete_account),
                ) {
                    navController.navigate(DeleteScreenNav.DeleteAccReasonScreen.route)
                }

                Spacer(modifier = Modifier.height(10.dp))

                val showLogoutDialog = remember { mutableStateOf(false) }

                if (showLogoutDialog.value) {
                    val activity = (LocalContext.current as? Activity)
                    LogOutDialog(
                        setShowDialog = {
                            showLogoutDialog.value = it
                        },
                        logout = {
                            viewModel.onEvent(MainFlowEvent.LogoutUserEvent(""))
                            context.launchActivity<WelcomeActivity> { }
                            val activity = context.getActivity()
                            activity?.finish()
                        }
                    )
                }

                ProfileOptionCard(
                    isVisibleRightIcon = true,
                    stringResource(R.string.logout),
                    ImageVector.vectorResource(id = R.drawable.ic_profile_logout),
                ) {
                    showLogoutDialog.value = true
                }
            }

            HeaderOnboarding()

        }
    }
}

@Composable
fun ProfileOptionCard(
    isVisibleRightIcon: Boolean = true,
    optionName: String,
    optionIcon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .clickable { onClick() }
            .background(Color.White)
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = optionIcon,
            contentDescription = "Navigation Icon",
            tint = Color.Black
        )

        Text(
            text = optionName,
            style = h4bold,
            modifier = Modifier.padding(start = 20.dp)
        )

        Spacer(
            Modifier
                .weight(1f)
                .background(Color.Red)
        )

        if (isVisibleRightIcon)
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right),
                contentDescription = "Navigation Icon",
                tint = Color.Black
            )
    }
}

