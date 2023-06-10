package com.bytecode.petsy.presentation.ui.screens.mainflow.brushing

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.commonui.buttons.IconTextButton
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.commonui.modals.AddDogDialog
import com.bytecode.petsy.presentation.ui.navigation.BottomBarScreen
import com.bytecode.petsy.presentation.ui.screens.mainflow.BrushingState
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowEvent
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowViewModel
import com.bytecode.petsy.presentation.ui.theme.ProgressColor
import com.bytecode.petsy.presentation.ui.theme.brushing_time_text
import com.bytecode.petsy.presentation.ui.theme.button_primary_text
import com.bytecode.petsy.presentation.ui.theme.paragraph_text
import com.bytecode.petsy.presentation.ui.theme.paused_text
import com.bytecode.petsy.presentation.ui.theme.ticker_text
import com.hitanshudhawan.circularprogressbar.CircularProgressBar
import kotlinx.coroutines.launch

@Composable
fun BrushingScreen(
    viewModel: MainFlowViewModel,
    navController: NavController
) {

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            PetsyImageBackground()
            HeaderOnboarding()

            when (viewModel.state.brushingPhase) {
                BrushingState.NOT_STARTED,
                BrushingState.IN_PROGRESS,
                BrushingState.CONTINUE,
                BrushingState.PAUSED -> {
                    BrushingTimerScreen(viewModel)
                }

                BrushingState.SAVING -> {
                    SaveBrushingScreen(viewModel)
                }

                BrushingState.SHARING -> {
                    ShareBrushingScreen(viewModel, navController)
                }
            }
        }

    }
}

@Composable
fun BrushingTimerScreen(viewModel: MainFlowViewModel) {
    val times by viewModel.brushingTime.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 110.dp),
            text = stringResource(R.string.brushing_timer),
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
            text = viewModel.formatTime(times),
            style = ticker_text,
        )


        if (viewModel.state.brushingPhase == BrushingState.PAUSED)
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 110.dp),
                text = stringResource(R.string.paused),
                style = paused_text,
            )

        when (viewModel.state.brushingPhase) {
            BrushingState.NOT_STARTED -> {
                GradientButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 125.dp),
                    text = stringResource(R.string.start_brushing),
                    onClick = {
                        viewModel.onEvent(MainFlowEvent.BrushingStateEvent(BrushingState.IN_PROGRESS))
                    }
                )
            }

            BrushingState.IN_PROGRESS,
            BrushingState.CONTINUE,
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
                                text = stringResource(R.string.pause),
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
                                        BrushingState.CONTINUE
                                    )
                                )
                            }) {
                            Text(
                                text = stringResource(R.string.btn_continue),
                                style = button_primary_text
                            )
                        }
                    }

                    GradientButton(
                        text = stringResource(R.string.finish_brushing),
                        onClick = {
                            viewModel.onEvent(MainFlowEvent.BrushingStateEvent(BrushingState.SAVING))
                        }
                    )
                }
            }

            BrushingState.SAVING -> {}

            BrushingState.SHARING -> {}
        }
    }
}

//BrushingFinishedDogScreen
@Composable
fun SaveBrushingScreen(viewModel: MainFlowViewModel) {
    val dogsListState = viewModel.dogsFlow.collectAsState()
    val lazyColumnListState = rememberLazyListState()
    val corroutineScope = rememberCoroutineScope()
    val times by viewModel.brushingTime.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val showDialog = remember { mutableStateOf(false) }

        if (showDialog.value)
            AddDogDialog(
                dogs = viewModel.getDogNames(),
                setShowDialog = {
                    showDialog.value = it
                },
                setValue = {
                    viewModel.onEvent(MainFlowEvent.SaveNewDog(it))
                })

        val (
            titleText,
            brushingTime,
            saveButton,
            backToBrushingButton,
            mainPart
        ) = createRefs()

        val topGuideline = createGuidelineFromTop(110.dp)
        val bottomGuideline = createGuidelineFromBottom(120.dp)

        Text(
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(topGuideline)
                    start.linkTo(parent.start)
                }
                .padding(horizontal = 20.dp),
            text = "Choose the dog whose teeth you brushed \uD83D\uDC47",
            style = MaterialTheme.typography.h1
        )

        Text(
            modifier = Modifier
                .constrainAs(brushingTime) {
                    top.linkTo(titleText.bottom)
                    start.linkTo(parent.start)
                }
                .padding(horizontal = 20.dp)
                .padding(top = 10.dp),
            text = "Brushing time:" + viewModel.formatTime(times),
            style = brushing_time_text
        )



        LazyColumn(
            modifier = Modifier
                .constrainAs(mainPart) {
                    top.linkTo(brushingTime.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(backToBrushingButton.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .padding(horizontal = 20.dp)
                .padding(top = 30.dp),
            state = lazyColumnListState,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(
                top = 5.dp,
                bottom = 5.dp
            )
        ) {

            if (viewModel.state.shouldScrollList) {
                corroutineScope.launch {
                    lazyColumnListState.scrollToItem(viewModel.getDogNames().size, 0)
                }
            }
            items(items = dogsListState.value,
                itemContent = { dog ->
                    DogItem(dog = dog, onClick = {
                        viewModel.onEvent(MainFlowEvent.DogClickedEvent(dog.id))
                    })
                }
            )

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                )
                {
                    IconTextButton(
                        modifier = Modifier
                            .padding(top = 5.dp),
                        onClick = {
                            showDialog.value = true
                        }
                    )
                }
            }
        }

        TextButton(
            modifier = Modifier
                .constrainAs(backToBrushingButton) {
                    bottom.linkTo(saveButton.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentPadding = PaddingValues(0.dp),
            onClick = {
                viewModel.onEvent(MainFlowEvent.BrushingStateEvent(BrushingState.PAUSED))
            }) {
            Text(
                text = "Back to brushing",
                style = button_primary_text
            )
        }

        GradientButton(
            modifier = Modifier
                .constrainAs(saveButton) {
                    bottom.linkTo(bottomGuideline)
                    start.linkTo(parent.start)
                }
                .height(70.dp),
            text = "Save",
            onClick = {
                viewModel.onEvent(MainFlowEvent.SaveBrushingTimeEvent(""))
            },
            alpha = if (viewModel.state.isDogSelected) 1f else 0.3f,
        )

    }
}

@Composable
fun ShareBrushingScreen(viewModel: MainFlowViewModel, navController: NavController) {
    val time by viewModel.finishedBrushingTime.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()

    ) {

        val (
            dogScoreView,
            gradientView,
            celebrateImage,
            titleText,
            descriptionText,
            btnShare,
            btnHome
        ) = createRefs()

        val topGuideline = createGuidelineFromTop(70.dp)
        val topGuidelineGradient = createGuidelineFromTop(20.dp)
        val topGuidelineForCelebration = createGuidelineFromTop(110.dp)
        val bottomGuideline = createGuidelineFromBottom(120.dp)

        Image(
            modifier = Modifier
                .constrainAs(gradientView) {
                    top.linkTo(topGuidelineGradient)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(400.dp)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.img_gradient),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )

        Image(
            modifier = Modifier
                .constrainAs(celebrateImage) {
                    top.linkTo(topGuidelineForCelebration)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(290.dp)
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            painter = painterResource(id = R.drawable.img_celebration),
            contentDescription = "",
            contentScale = ContentScale.FillHeight
        )

        viewModel.getSelectedDog()?.let {
            Column(
                modifier = Modifier
                    .constrainAs(dogScoreView) {
                        top.linkTo(topGuideline)
                        centerHorizontallyTo(parent)
                    }
                    .padding(top = 30.dp)
            ) {
                DogShareView(
                    dog = it,
                    time = viewModel.formatTime(time),
                )
            }
        }

        Text(
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(celebrateImage.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 20.dp),
            text = "Keep up the good work",
            style = MaterialTheme.typography.h2
        )

        Text(
            modifier = Modifier
                .constrainAs(descriptionText) {
                    top.linkTo(titleText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 20.dp)
                .padding(top = 15.dp),
            text = "Thank you for looking up for your dog\n" +
                    "and brushing his/her teeth.\n" +
                    "Have a a great day",
            textAlign = TextAlign.Center,
            style = paragraph_text
        )

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "This is my dog.")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        val context = LocalContext.current


        TextButton(
            modifier = Modifier
                .constrainAs(btnShare) {
                    bottom.linkTo(btnHome.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentPadding = PaddingValues(0.dp),
            onClick = {
                context.startActivity(shareIntent)
            }) {
            Text(
                text = "Share",
                style = button_primary_text
            )
        }

        GradientButton(
            modifier = Modifier
                .constrainAs(btnHome) {
                    bottom.linkTo(bottomGuideline)
                    start.linkTo(parent.start)
                }
                .height(70.dp),
            text = "Home",
            onClick = {
                viewModel.onEvent(MainFlowEvent.BrushingStateEvent(BrushingState.NOT_STARTED))
                navController.navigate(BottomBarScreen.DashboardScreen.route)
            }
        )
    }
}


@Preview
@Composable
fun FinishedScreenPreview() {
//    BrushingFinishedDogScreen()
}

