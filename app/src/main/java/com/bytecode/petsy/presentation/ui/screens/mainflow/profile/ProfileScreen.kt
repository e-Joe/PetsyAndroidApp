package com.bytecode.petsy.presentation.ui.screens.mainflow.profile

import android.widget.Toast
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.theme.ScreenBackgroundColor
import com.bytecode.petsy.presentation.ui.theme.h4bold

@Composable
fun ProfileScreen() {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
                .background(ScreenBackgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 130.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val context = LocalContext.current
                Spacer(modifier = Modifier.height(100.dp))

                Text(
                    text = "Ime i prezime",
                    style = MaterialTheme.typography.h1,
                )

                Text(
                    text = "marko,maric@gmail.com",
                    style = MaterialTheme.typography.h4,
                )

                Spacer(modifier = Modifier.height(70.dp))

                ProfileOptionCard(
                    "My Pets",
                    ImageVector.vectorResource(id = R.drawable.ic_profile_my_pets)
                ) { Toast.makeText(context, "My Pets", Toast.LENGTH_SHORT).show() }

                Spacer(modifier = Modifier.height(10.dp))

                ProfileOptionCard(
                    "Tutorials/Education",
                    ImageVector.vectorResource(id = R.drawable.ic_profile_tutorials_education),
                ) { Toast.makeText(context, "Tutorials/Education", Toast.LENGTH_SHORT).show() }

                Spacer(modifier = Modifier.height(10.dp))

                ProfileOptionCard(
                    "Change password",
                    ImageVector.vectorResource(id = R.drawable.ic_profile_change_password),
                )
                { Toast.makeText(context, "Change password", Toast.LENGTH_SHORT).show() }

                Spacer(modifier = Modifier.height(10.dp))

                ProfileOptionCard(
                    "Privacy",
                    ImageVector.vectorResource(id = R.drawable.ic_profile_privacy),
                ) { Toast.makeText(context, "Privacy", Toast.LENGTH_SHORT).show() }

                Spacer(modifier = Modifier.height(10.dp))

                ProfileOptionCard(
                    "Delete account",
                    ImageVector.vectorResource(id = R.drawable.ic_profile_delete_account),
                ) { Toast.makeText(context, "Delete account", Toast.LENGTH_SHORT).show() }

                Spacer(modifier = Modifier.height(10.dp))

                ProfileOptionCard(
                    "Logout",
                    ImageVector.vectorResource(id = R.drawable.ic_profile_logout),
                ) { Toast.makeText(context, "Logout", Toast.LENGTH_SHORT).show() }
            }

            HeaderOnboarding()

        }
    }
}

@Composable
private fun ProfileOptionCard(optionName: String, optionIcon: ImageVector, onClick: () -> Unit) {
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
            contentDescription = "Navigation Icon"
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

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right),
            contentDescription = "Navigation Icon"
        )
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    ProfileScreen()
}