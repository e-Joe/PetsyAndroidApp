package com.bytecode.petsy.presentation.ui.screens.mainflow.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.navigation.BottomBarScreen
import com.bytecode.petsy.presentation.ui.navigation.LoginFlowScreen
import com.bytecode.petsy.presentation.ui.screens.mainflow.BrushingState
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowEvent
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowViewModel

@Composable
fun DashboardScreen(viewModel: MainFlowViewModel, navController: NavHostController) {

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            PetsyImageBackground()
            EmptyStateDashboard(viewModel, navController)
            HeaderOnboarding()
        }
    }
}

@Composable
private fun EmptyStateDashboard(viewModel: MainFlowViewModel, navController: NavHostController) {

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 110.dp)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = stringResource(R.string.watch_short_tutorial),
                style = MaterialTheme.typography.h1,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                modifier = Modifier
                    .aspectRatio(ratio = 1f)
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.img_video_tutorial),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.no_activities_yet),
                style = MaterialTheme.typography.h1,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.washing_teets_description),
                style = MaterialTheme.typography.h4,
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        GradientButton(
            modifier = Modifier.padding(bottom = 15.dp, top = 15.dp),
            paddingEnd = 0.dp,
            paddingStart = 0.dp,
            text = if (viewModel.state.brushingPhase == BrushingState.IN_PROGRESS)
                "Started"
            else
                "Start brushing",
            onClick = {
                viewModel.onEvent(MainFlowEvent.BrushingStateEvent(BrushingState.IN_PROGRESS))
                navController.navigate(BottomBarScreen.BrushingScreen.route)
            }
        )

    }
}

//@Preview
//@Composable
//fun DashboardScreenPreview() {
//    DashboardScreen()
//}