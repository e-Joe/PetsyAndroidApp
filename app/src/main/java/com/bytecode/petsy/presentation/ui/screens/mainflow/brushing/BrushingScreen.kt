package com.bytecode.petsy.presentation.ui.screens.mainflow.brushing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.screens.mainflow.BrushingState
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowEvent
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowViewModel
import com.bytecode.petsy.presentation.ui.theme.ProgressColor
import com.bytecode.petsy.presentation.ui.theme.button_primary_text
import com.bytecode.petsy.presentation.ui.theme.paused_text
import com.bytecode.petsy.presentation.ui.theme.ticker_text
import com.hitanshudhawan.circularprogressbar.CircularProgressBar

@Composable
fun BrushingScreen(
    viewModel: MainFlowViewModel
) {

    Scaffold { paddingValues ->

        val times by viewModel.times.collectAsState()
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {


            PetsyImageBackground()
            HeaderOnboarding()

            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 110.dp),
                text = "Brushing timer",
                style = MaterialTheme.typography.h2
            )

            Image(
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .size(350.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.img_gradient),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )

            CircularProgressBar(
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .size(220.dp)
                    .align(Alignment.Center),
                progress = times.toFloat(),
                progressMax = 120f,
                progressBarColor = ProgressColor,
                progressBarWidth = 15.dp,
                backgroundProgressBarColor = Color.White,
                backgroundProgressBarWidth = 15.dp,
                roundBorder = true,
                startAngle = 0f
            )

            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 40.dp),
                text = "$times",
                style = ticker_text,
            )


            if (viewModel.state.brushingPhase == BrushingState.PAUSED)
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 110.dp),
                    text = "Paused",
                    style = paused_text,
                )

            when (viewModel.state.brushingPhase) {
                BrushingState.NOT_STARTED -> {
                    GradientButton(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 125.dp),
                        text = "Start brushing",
                        onClick = {
                            viewModel.onEvent(MainFlowEvent.BrushingStateEvent(BrushingState.IN_PROGRESS))
                        }
                    )
                }

                BrushingState.IN_PROGRESS,
                BrushingState.PAUSED -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 125.dp),
                    ) {

                        if (viewModel.state.brushingPhase == BrushingState.IN_PROGRESS) {
                            TextButton(
                                modifier = Modifier
                                    .height(70.dp)
                                    .align(Alignment.CenterHorizontally),
                                contentPadding = PaddingValues(0.dp),
                                onClick = {
                                    viewModel.onEvent(
                                        MainFlowEvent.BrushingStateEvent(
                                            BrushingState.PAUSED
                                        )
                                    )
                                }) {
                                Text(
                                    text = "Pause",
                                    style = button_primary_text
                                )
                            }
                        } else {
                            TextButton(
                                modifier = Modifier
                                    .height(70.dp)
                                    .align(Alignment.CenterHorizontally),
                                contentPadding = PaddingValues(0.dp),
                                onClick = {
                                    viewModel.onEvent(
                                        MainFlowEvent.BrushingStateEvent(
                                            BrushingState.IN_PROGRESS
                                        )
                                    )
                                }) {
                                Text(
                                    text = "Continue",
                                    style = button_primary_text
                                )
                            }
                        }

                        GradientButton(
                            text = "Finish brushing",
                            onClick = {
                                viewModel.onEvent(MainFlowEvent.BrushingStateEvent(BrushingState.NOT_STARTED))
                            }
                        )
                    }
                }
            }

        }
    }
}

//@Preview
//@Composable
//fun DashboardScreenPreview() {
//    BrushingScreen()
//}