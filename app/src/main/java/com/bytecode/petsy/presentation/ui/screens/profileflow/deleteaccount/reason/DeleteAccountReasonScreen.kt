package com.bytecode.petsy.presentation.ui.screens.profileflow.deleteaccount.reason

import android.widget.Space
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.navigation.DeleteScreenNav
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowViewModel
import com.bytecode.petsy.presentation.ui.theme.NavigationItemActiveColor
import com.bytecode.petsy.presentation.ui.theme.h4bold

@Composable
fun DeleteAccountReasonScreen(
    viewModel: MainFlowViewModel, navController: NavHostController = rememberNavController()
) {

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            PetsyImageBackground()
            HeaderOnboarding()
            DeleteAccountContent(navController)
        }
    }
}

@Composable
fun DeleteAccountContent(navController: NavHostController) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        val (titleText, mainPart) = createRefs()

        val topGuideline = createGuidelineFromTop(110.dp)
        val bottomGuideline = createGuidelineFromBottom(20.dp)

        Text(modifier = Modifier
            .constrainAs(titleText) {
                top.linkTo(topGuideline)
                start.linkTo(parent.start)
            }
            .padding(horizontal = 20.dp), text = "Account", style = MaterialTheme.typography.h1)

        Column(modifier = Modifier
            .constrainAs(mainPart) {
                top.linkTo(titleText.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bottomGuideline)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
            .padding(horizontal = 20.dp)
            .padding(top = 30.dp)) {
            Text(
                modifier = Modifier.padding(bottom = 20.dp),
                text = "Why are you deleting your account?", style = MaterialTheme.typography.h4
            )

            DeleteInfoCard(
                "I don’t need it anymore",
                ImageVector.vectorResource(id = R.drawable.ic_circle_not_selected)
            ) {
                navController.navigate(DeleteScreenNav.DeleteAccInfoScreen.route)
            }

            Spacer(modifier = Modifier.height(20.dp))

            DeleteInfoCard(
                "App is hard to use",
                ImageVector.vectorResource(id = R.drawable.ic_circle_not_selected)
            ) {
                navController.navigate(DeleteScreenNav.DeleteAccInfoScreen.route)
            }

            Spacer(modifier = Modifier.height(20.dp))

            DeleteInfoCard(
                "I’m switching to something else",
                ImageVector.vectorResource(id = R.drawable.ic_circle_not_selected)
            ) {
                navController.navigate(DeleteScreenNav.DeleteAccInfoScreen.route)
            }

            Spacer(modifier = Modifier.height(20.dp))

            DeleteInfoCard(
                "Other",
                ImageVector.vectorResource(id = R.drawable.ic_circle_not_selected)
            ) {
                navController.navigate(DeleteScreenNav.DeleteAccInfoScreen.route)
            }

        }
    }
}

@Composable
fun DeleteInfoCard(
    optionName: String, optionIcon: ImageVector, onClick: () -> Unit
) {
    Row(
        modifier = Modifier.clickable { onClick() }, verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = optionIcon, contentDescription = "Navigation Icon", tint = NavigationItemActiveColor
        )

        Text(
            text = optionName,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}