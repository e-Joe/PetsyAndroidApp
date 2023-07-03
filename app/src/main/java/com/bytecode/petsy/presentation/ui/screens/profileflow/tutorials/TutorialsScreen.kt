package com.bytecode.petsy.presentation.ui.screens.profileflow.tutorials

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.navigation.ProfileScreenNav
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowViewModel
import com.bytecode.petsy.presentation.ui.screens.mainflow.profile.ProfileOptionCard

@Composable
fun TutorialsScreen(
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
            TutorialsContent()
        }
    }
}

@Composable
fun TutorialsContent() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

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
            text = stringResource(R.string.tutorials),
            style = MaterialTheme.typography.h1
        )

        val context = LocalContext.current
        val howToBrush = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://petsie.pet/faq/#1680258613020-76225f68-7a69e400-3640")) }
        val howToMaintainDogs = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://petsie.pet/faq/#1682339243478-9df677cb-4024")) }
        val bestFood = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://petsie.pet/faq/#1682339279240-495a13c0-5336")) }
        val badFood = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://petsie.pet/faq/#1680258613020-76225f68-7a69e400-3640")) }

        Column(
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
                .padding(top = 30.dp)
        ) {
            ProfileOptionCard(
                isVisibleRightIcon = false,
                stringResource(R.string.how_to_brush_you_pets_teeth),
                ImageVector.vectorResource(id = R.drawable.ic_profile_tutorials_education),
            ) {
                context.startActivity(howToBrush)
            }

            Spacer(modifier = Modifier.height(10.dp))

            ProfileOptionCard(
                isVisibleRightIcon = false,
                stringResource(R.string.how_to_maintain_dogs_teeth),
                ImageVector.vectorResource(id = R.drawable.ic_profile_tutorials_education),
            ) {
                context.startActivity(howToMaintainDogs)
            }

            Spacer(modifier = Modifier.height(10.dp))

            ProfileOptionCard(
                isVisibleRightIcon = false,
                stringResource(R.string.best_foods_for_dogs),
                ImageVector.vectorResource(id = R.drawable.ic_profile_tutorials_education),
            ) {
                context.startActivity(bestFood)
            }

            Spacer(modifier = Modifier.height(10.dp))

            ProfileOptionCard(
                isVisibleRightIcon = false,
                stringResource(R.string.bad_foods_for_your_dog),
                ImageVector.vectorResource(id = R.drawable.ic_profile_tutorials_education),
            ) {
                context.startActivity(badFood)
            }

        }
    }
}