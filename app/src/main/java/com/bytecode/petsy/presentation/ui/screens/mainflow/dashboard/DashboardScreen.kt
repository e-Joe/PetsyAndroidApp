package com.bytecode.petsy.presentation.ui.screens.mainflow.dashboard

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bytecode.petsy.R
import com.bytecode.petsy.data.model.dto.dog.DogDto
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.navigation.BottomBarScreen
import com.bytecode.petsy.presentation.ui.navigation.LoginFlowScreen
import com.bytecode.petsy.presentation.ui.screens.mainflow.BrushingState
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowEvent
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowViewModel
import com.bytecode.petsy.presentation.ui.screens.mainflow.brushing.DogItem
import com.bytecode.petsy.util.toColor
import com.bytecode.petsy.util.toLighterColor

@Composable
fun DashboardScreen(viewModel: MainFlowViewModel, navController: NavHostController) {

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            PetsyImageBackground()
//            EmptyStateDashboard(viewModel, navController)
            PetsDataScreen(viewModel = viewModel)
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

@Composable
private fun PetsDataScreen(viewModel: MainFlowViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 110.dp, top = 70.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Choose your pet",
            style = MaterialTheme.typography.h2,
        )
        Spacer(modifier = Modifier.height(17.dp))
        YourPetsView(viewModel)
        ChartAreaView()
        TodaysBrushingView()
    }
}

@Composable
private fun ChartAreaView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            modifier = Modifier
                .aspectRatio(ratio = 1f)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.img_chart_temp),
            contentDescription = ""
        )
    }
}

@Composable
private fun YourPetsView(viewModel: MainFlowViewModel) {
    val dogsListState = viewModel.dogsFlow.collectAsState()
    val lazyColumnListState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyRow(
            state = lazyColumnListState,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(
                top = 5.dp,
                bottom = 5.dp
            )
        ) {
            items(items = dogsListState.value,
                itemContent = { dog ->
                    ChartDogView(dog = dog, onClick = {
                        viewModel.onEvent(MainFlowEvent.ChartDogClickedEvent(dog.id))
                    })
                }
            )
        }
    }
}

@Composable
fun ChartDogView(
    onClick: () -> Unit = {},
    dog: DogDto
) {
    var background = Color.White.copy()
    var border = Color.Transparent
    var borderWidth = 0.dp

    if (dog.isSelectedForChart) {
        background = dog.color.toLighterColor()
        border = dog.color.toColor()
        borderWidth = 2.dp
    }

    Card(
        modifier = Modifier
            .height(31.dp)
            .clickable {
                onClick()
            },
        elevation = 3.dp,
        shape = RoundedCornerShape(size = 15.dp),
        backgroundColor = background,
        border = BorderStroke(width = borderWidth, color = border)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(horizontal = 10.dp)
                .padding(vertical = 2.dp),
            contentAlignment = Alignment.Center,

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.background(Color.Transparent),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_paw),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(dog.color.toColor())
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = dog.name,
                    style = MaterialTheme.typography.h2
                )
            }
        }
    }
}

@Composable
fun TodaysBrushingView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(115.dp)
            .padding(horizontal = 10.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.7f))
            .padding(20.dp)
    ) {
        Text(
            text = "Today’s brushing",
            style = MaterialTheme.typography.h2,
        )
    }
}