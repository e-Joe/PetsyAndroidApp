package com.bytecode.petsy.presentation.ui.screens.mainflow.brushing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.screens.loginflow.login.LoginFormEvent
import com.bytecode.petsy.presentation.ui.theme.ProgressColor
import com.bytecode.petsy.presentation.ui.theme.paused_text
import com.bytecode.petsy.presentation.ui.theme.ticker_text
import com.hitanshudhawan.circularprogressbar.CircularProgressBar

@Composable
fun BrushingScreen() {

    Scaffold { paddingValues ->
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
                progress = 30f,
                progressMax = 100f,
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
                text = "00:00",
                style = ticker_text,
            )

            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 110.dp),
                text = "Paused",
                style = paused_text,
            )

            GradientButton(
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 130.dp),
                text = "Start brushing",
                onClick = {

                }
            )
        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    BrushingScreen()
}