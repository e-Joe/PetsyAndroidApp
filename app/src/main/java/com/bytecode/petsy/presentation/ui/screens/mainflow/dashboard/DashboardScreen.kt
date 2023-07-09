package com.bytecode.petsy.presentation.ui.screens.mainflow.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.bytecode.framework.extension.formatDateTimeShortMonth
import com.bytecode.framework.extension.isToday
import com.bytecode.petsy.R
import com.bytecode.petsy.data.model.dto.brushing.PetsieChartData
import com.bytecode.petsy.data.model.dto.dog.DogDto
import com.bytecode.petsy.data.model.dto.dog.calculatePercentage
import com.bytecode.petsy.data.model.dto.dog.calculatePercentageRounded
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.commonui.custom.CustomLinearProgressIndicator
import com.bytecode.petsy.presentation.ui.commonui.custom.chart.PetsieChart
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.navigation.BottomBarScreen
import com.bytecode.petsy.presentation.ui.screens.mainflow.BrushingState
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowEvent
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowViewModel
import com.bytecode.petsy.presentation.ui.theme.TextSecondary2
import com.bytecode.petsy.presentation.ui.theme.brushingCardPercentageText
import com.bytecode.petsy.presentation.ui.theme.brushingCardText
import com.bytecode.petsy.presentation.ui.theme.brushingCardTextBold
import com.bytecode.petsy.presentation.ui.theme.brushingCardTimeText
import com.bytecode.petsy.presentation.ui.theme.chartNavigationDates
import com.bytecode.petsy.presentation.ui.theme.chartTitle
import com.bytecode.petsy.util.toColor
import com.bytecode.petsy.util.toLighterColor
import com.bytecode.petsy.util.toMediumColor

@Composable
fun DashboardScreen(viewModel: MainFlowViewModel, navController: NavHostController) {

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            val showTutorial = viewModel.showTutorialVideoFlow.collectAsState()

            PetsyImageBackground()

            if (showTutorial.value)
                EmptyStateDashboard(viewModel, navController)
            else
                PetsDataScreen(viewModel = viewModel)
            HeaderOnboarding()
        }
    }
}

@Composable
private fun EmptyStateDashboard(viewModel: MainFlowViewModel, navController: NavHostController) {

    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 110.dp)
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
//            Image(
//                modifier = Modifier
//                    .aspectRatio(ratio = 1f)
//                    .fillMaxWidth()
//                    .clickable {
//                        navController.navigate(VideoScreenNav.VideoScreen.route)
//                    },
//                painter = painterResource(id = R.drawable.img_video_tutorial),
//                contentDescription = ""
//            )
            VideoView()
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

        GradientButton(modifier = Modifier.padding(bottom = 15.dp, top = 15.dp),
            paddingEnd = 0.dp,
            paddingStart = 0.dp,
            text = if (viewModel.state.brushingPhase == BrushingState.IN_PROGRESS) stringResource(R.string.started)
            else stringResource(R.string.start_brushing),
            onClick = {
                viewModel.onEvent(MainFlowEvent.BrushingStateEvent(BrushingState.IN_PROGRESS))
                navController.navigate(BottomBarScreen.BrushingScreen.route)
            })

    }
}

@Composable
private fun PetsDataScreen(viewModel: MainFlowViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 110.dp, top = 70.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.choose_your_pet),
            style = MaterialTheme.typography.h2,
        )
        Spacer(modifier = Modifier.height(20.dp))
        YourPetsView(viewModel)
        Spacer(modifier = Modifier.height(20.dp))
        ChartAreaView(viewModel)
        Spacer(modifier = Modifier.height(20.dp))
        TodaysBrushingView(viewModel)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.recent_activities),
            style = MaterialTheme.typography.h2,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.recommended_daily_brushing),
            style = MaterialTheme.typography.h4,
        )
        Spacer(modifier = Modifier.height(20.dp))
        RecentActivities(viewModel)
    }
}

@Composable
private fun ChartAreaView(mainFlowViewModel: MainFlowViewModel) {
    val chartPeriodDates = mainFlowViewModel.formattedChartPeriodFlow.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(288.dp),
        shape = RoundedCornerShape(size = 15.dp),
        backgroundColor = Color.White,
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .padding(bottom = 25.dp)

        ) {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (title, previousButton, datesView, nextButton, chart) = createRefs()

                Text(
                    modifier = Modifier
                        .constrainAs(title) {
                            top.linkTo(previousButton.top)
                            start.linkTo(parent.start)
                            bottom.linkTo(previousButton.bottom)
                        },
                    text = stringResource(R.string.seconds),
                    style = chartTitle
                )

                Image(
                    painter = painterResource(R.drawable.ic_next),
                    contentDescription = "next",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .size(31.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(0.5.dp, TextSecondary2, CircleShape)
                        .constrainAs(nextButton) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .clickable {
                            mainFlowViewModel.onEvent(MainFlowEvent.NextPeriodClick(""))
                        }
                )

                Column(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .height(31.dp)
                        .width(98.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(0.5.dp, TextSecondary2, CircleShape)
                        .constrainAs(datesView) {
                            top.linkTo(parent.top)
                            end.linkTo(nextButton.start)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = chartPeriodDates.value,
                        style = chartNavigationDates
                    )
                }

                Image(
                    painter = painterResource(R.drawable.ic_previous),
                    contentDescription = "previous",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(31.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(0.5.dp, TextSecondary2, CircleShape)
                        .constrainAs(previousButton) {
                            top.linkTo(parent.top)
                            end.linkTo(datesView.start)
                        }
                        .clickable {
                            mainFlowViewModel.onEvent(MainFlowEvent.PreviousPeriodClick(""))
                        }
                )

                Box(modifier = Modifier
                    .padding(top = 18.dp)
                    .constrainAs(chart) {
                        top.linkTo(previousButton.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }) {

                    val petsieChartDataState =
                        mainFlowViewModel.petsieChartDataFlow.collectAsState()

                    PetsieChart(petsieChartDataState.value)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun YourPetsView(viewModel: MainFlowViewModel) {
    val dogsListState = viewModel.dogsFlow.collectAsState()
    val lazyColumnListState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            LazyRow(
                state = lazyColumnListState,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(
                    top = 5.dp, bottom = 5.dp
                )
            ) {
                items(items = dogsListState.value, itemContent = { dog ->
                    ChartDogView(dog = dog, onClick = {
                        viewModel.onEvent(MainFlowEvent.ChartDogClickedEvent(dog.id))
                    })
                })
            }
        }
    }
}

@Composable
fun ChartDogView(
    onClick: () -> Unit = {}, dog: DogDto, modifier: Modifier = Modifier
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
        modifier = modifier
            .height(31.dp)
            .clickable {
                onClick()
            },
        elevation = 0.dp,
        shape = RoundedCornerShape(size = 15.dp),
        backgroundColor = background,
        border = BorderStroke(width = borderWidth, color = border)
    ) {
        Box(
            modifier = Modifier
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
                    style = MaterialTheme.typography.h2,
                    color = dog.color.toColor()
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodaysBrushingView(viewModel: MainFlowViewModel) {
    val dogsListState = viewModel.dogsFlow.collectAsState()
    val lazyColumnListState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.7f))
    ) {
        Text(
            text = stringResource(R.string.today_s_brushing),
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(top = 20.dp, start = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 20.dp),
                state = lazyColumnListState,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(
                    top = 5.dp, bottom = 5.dp
                )
            ) {
                items(items = dogsListState.value, itemContent = { dog ->
                    TodayDogView(dog = dog, onClick = {})
                })
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun TodayDogView(
    onClick: () -> Unit = {}, dog: DogDto
) {

    var background = dog.color.toLighterColor()
    var textColor = dog.color.toColor()
    var icon = R.drawable.ic_not_checked_today

    if (dog.lastBrushingDate.isToday()) {
        icon = R.drawable.ic_checked_today
    }


    Card(
        modifier = Modifier
            .height(31.dp)
            .clickable {
                onClick()
            },
        elevation = 0.dp,
        shape = RoundedCornerShape(size = 15.dp),
        backgroundColor = background
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
                    painter = painterResource(icon),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = dog.name, style = MaterialTheme.typography.h2, color = textColor
                )
            }
        }
    }
}


@Composable
fun RecentActivities(viewModel: MainFlowViewModel) {
    val dogsListState = viewModel.dogsFlow.collectAsState()

    Column() {
        if (dogsListState.value.isNotEmpty()) {
            val firstDog = dogsListState.value[0]

            if (firstDog.lastBrushingPeriod > 0) {
                RecentActivityView(firstDog) // TODO Add check
                Spacer(modifier = Modifier.height(20.dp))
            }

            if (dogsListState.value.size >= 2) {
                val secondDog = dogsListState.value[1]
                if (secondDog.lastBrushingPeriod > 0) {
                    RecentActivityView(secondDog)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun RecentActivityView(dog: DogDto) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(dog.color.toColor())
            .padding(bottom = 3.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(20.dp))
                .background(dog.color.toLighterColor())
                .padding(15.dp)
        ) {


            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (brushingTextFirstPart, brushingTextSecondPart, dogView, progress, spacer, progressText, time) = createRefs()

                Text(
                    text = stringResource(R.string.brushing_duration),
                    style = brushingCardText,
                    modifier = Modifier.constrainAs(brushingTextFirstPart) {
                        top.linkTo(parent.top)
                        bottom.linkTo(dogView.bottom)
                        start.linkTo(parent.start)
                    },
                    textAlign = TextAlign.Left
                )

                Text(
                    text = stringResource(
                        id = R.string.brushing_duration_time,
                        dog.lastBrushingPeriod
                    ),
                    style = brushingCardTextBold,
                    modifier = Modifier.constrainAs(brushingTextSecondPart) {
                        top.linkTo(dogView.top)
                        bottom.linkTo(dogView.bottom)
                        start.linkTo(brushingTextFirstPart.end)
                    },
                    textAlign = TextAlign.Left
                )

                Box(
                    modifier = Modifier.constrainAs(dogView) {
                        top.linkTo(parent.top)
                        start.linkTo(brushingTextSecondPart.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }, contentAlignment = Alignment.CenterEnd
                ) {
                    RecentDogView(
                        dog = dog
                    )
                }

                Spacer(modifier = Modifier
                    .constrainAs(spacer) {
                        top.linkTo(dogView.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .height(25.dp))


                CustomLinearProgressIndicator(modifier = Modifier
                    .constrainAs(progress) {
                        top.linkTo(progressText.top)
                        bottom.linkTo(progressText.bottom)
                        start.linkTo(brushingTextFirstPart.start)
                        end.linkTo(brushingTextSecondPart.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(end = 10.dp, top = 2.dp),
                    progressColor = dog.color.toColor(),
                    progress = dog.calculatePercentage().toFloat())

                Text(
                    text = "${dog.calculatePercentageRounded()}%",
                    style = brushingCardPercentageText,
                    modifier = Modifier.constrainAs(progressText) {
                        top.linkTo(spacer.bottom)
                        start.linkTo(progress.end)
                    },
                    textAlign = TextAlign.Left,
                    color = dog.color.toColor()
                )

                Text(
                    text = dog.lastBrushingDate.formatDateTimeShortMonth(),
                    style = brushingCardTimeText,
                    modifier = Modifier.constrainAs(time) {
                        top.linkTo(progressText.top)
                        end.linkTo(parent.end)
                    },
                    textAlign = TextAlign.Left,
                    color = dog.color.toColor()
                )
            }
        }
    }
}

@Composable
fun RecentDogView(
    onClick: () -> Unit = {}, dog: DogDto, modifier: Modifier = Modifier
) {
    var background = dog.color.toMediumColor()

    Card(
        modifier = modifier
            .height(31.dp)
            .padding(start = 3.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(size = 15.dp),
        backgroundColor = background
    ) {
        Box(
            modifier = Modifier
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
                    style = MaterialTheme.typography.h2,
                    color = dog.color.toColor()
                )
            }
        }
    }
}

