package com.bytecode.petsy.presentation.ui.screens.mainflow.dashboard

import android.widget.ImageView.ScaleType
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding

@Composable
fun DashboardScreen() {

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            PetsyImageBackground()
            HeaderOnboarding()
            EmptyStateDashboard()

        }
    }
}

@Composable
private fun EmptyStateDashboard() {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 110.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Watch short tutorial",
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
            text = "No activities yet",
            style = MaterialTheme.typography.h1,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Did you know that by brushing your dog’s teeth every day for 2 minutes, could prolong his life by 3 years? (or some similar text)",
            style = MaterialTheme.typography.h4,
        )
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
}