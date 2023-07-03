package com.bytecode.petsy.presentation.ui.screens.profileflow.deleteaccount.info

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.navigation.BottomBarScreen
import com.bytecode.petsy.presentation.ui.navigation.DeleteScreenNav
import com.bytecode.petsy.presentation.ui.screens.mainflow.BrushingState
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowEvent
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowViewModel
import com.bytecode.petsy.presentation.ui.theme.InputTextErrorColor
import com.bytecode.petsy.presentation.ui.theme.NavigationItemActiveColor
import com.bytecode.petsy.presentation.ui.theme.h4bold

@Composable
fun DeleteAccountInfoScreen(
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

        val (
            titleText,
            mainPart,
            deleteProfileBtn
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
            text = stringResource(R.string.account),
            style = MaterialTheme.typography.h1,
        )

        Column(
            modifier = Modifier
                .constrainAs(mainPart) {
                    top.linkTo(titleText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(deleteProfileBtn.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .padding(horizontal = 20.dp)
                .padding(top = 30.dp)
        ) {

            Text(
                modifier = Modifier.padding(bottom = 20.dp),
                text = stringResource(R.string.deleting_account_will_do_the_following), style = MaterialTheme.typography.h4
            )

            DeleteInfoCard(
                stringResource(R.string.log_you_out_on_all_devices),
                ImageVector.vectorResource(id = R.drawable.icon_delete_info)
            )
            Spacer(modifier = Modifier.height(20.dp))

            DeleteInfoCard(
                stringResource(R.string.delete_all_of_your_account_information),
                ImageVector.vectorResource(id = R.drawable.icon_delete_info)
            )

        }

        GradientButton(
            modifier = Modifier
                .constrainAs(deleteProfileBtn) {
                    bottom.linkTo(bottomGuideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(70.dp),
            text = stringResource(R.string.delete_profile),
            onClick = {
                navController.navigate(DeleteScreenNav.DeleteAccPasswordScreen.route)
            }
        )
    }
}

@Composable
fun DeleteInfoCard(
    optionName: String, optionIcon: ImageVector
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = optionIcon,
            contentDescription = "Navigation Icon",
            tint = InputTextErrorColor
        )

        Text(
            text = optionName,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}

