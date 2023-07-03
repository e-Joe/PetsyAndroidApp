package com.bytecode.petsy.presentation.ui.screens.profileflow.mypets

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.buttons.IconTextButton
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.commonui.modals.AddDogDialog
import com.bytecode.petsy.presentation.ui.commonui.modals.DeleteDogDialog
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowEvent
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowViewModel
import com.bytecode.petsy.presentation.ui.screens.mainflow.brushing.DogItem
import kotlinx.coroutines.launch

@Composable
fun MyPetsProfileScreen(
    viewModel: MainFlowViewModel,
    navController: NavHostController = rememberNavController()
) {

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            PetsyImageBackground()
            HeaderOnboarding()
            DogsList(viewModel)
        }
    }
}

@Composable
fun DogsList(viewModel: MainFlowViewModel) {
    val dogsListState = viewModel.dogsFlow.collectAsState()
    val lazyColumnListState = rememberLazyListState()
    val corroutineScope = rememberCoroutineScope()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        val showDialog = remember { mutableStateOf(false) }
        val showDeleteDialog = remember { mutableStateOf(false) }

        if (showDialog.value)
            AddDogDialog(
                dogs = viewModel.getDogNames(),
                setShowDialog = {
                    showDialog.value = it
                },
                setValue = {
                    viewModel.onEvent(MainFlowEvent.SaveNewDog(it))
                })

        if (showDeleteDialog.value)
            DeleteDogDialog(
                setShowDialog = {
                    showDeleteDialog.value = it
                },
                deleteDog = {
                    viewModel.onEvent(MainFlowEvent.DeleteDogConfirmedEvent(""))
                },
                dogName = viewModel.deleteDog.name
            )

        val (
            titleText,
            mainPart
        ) = createRefs()

        val topGuideline = createGuidelineFromTop(110.dp)
        val bottomGuideline = createGuidelineFromBottom(20.dp)

        Text(
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(topGuideline)
                    start.linkTo(parent.start)
                }
                .padding(horizontal = 20.dp),
            text = "My Pets",
            style = MaterialTheme.typography.h1
        )

        LazyColumn(
            modifier = Modifier
                .constrainAs(mainPart) {
                    top.linkTo(titleText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomGuideline)
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
                    DogItemMyPets(dog = dog, onClick = {
                        showDeleteDialog.value = true
                        viewModel.onEvent(MainFlowEvent.DeleteDogClickEvent(dog))
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
    }
}