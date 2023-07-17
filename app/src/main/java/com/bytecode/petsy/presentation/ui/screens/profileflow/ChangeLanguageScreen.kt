package com.bytecode.petsy.presentation.ui.screens.profileflow

import android.app.LocaleConfig
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bytecode.petsy.R
import com.bytecode.petsy.data.model.dto.language.LanguageDto
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.commonui.modals.PasswordChangedDialog
import com.bytecode.petsy.presentation.ui.navigation.ProfileScreenNav
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowEvent
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowViewModel
import com.bytecode.petsy.presentation.ui.theme.ProgressColor
import com.bytecode.petsy.presentation.ui.theme.h4bold
import com.bytecode.petsy.util.LocaleUtils
import com.bytecode.petsy.util.localeToEmoji
import kotlin.coroutines.coroutineContext

@Composable
fun ChangeLanguageScreen(
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
            ChangeLanguageContent(
                viewModel,
                navController
            )
        }
    }
}

@Composable
fun ChangeLanguageContent(viewModel: MainFlowViewModel, navController: NavHostController) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        val context = LocalContext.current
        val english by viewModel.englishLanguageFlow.collectAsState()
        val denmark by viewModel.denmarkLanguageFlow.collectAsState()
        val romanian by viewModel.romanianLanguageFlow.collectAsState()
        val serbian by viewModel.serbianLanguageFlow.collectAsState()

        val (
            titleText,
            mainPart,
            saveButton,
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
            text = stringResource(R.string.change_language_title),
            style = MaterialTheme.typography.h1
        )

        Column(
            modifier = Modifier
                .constrainAs(mainPart) {
                    top.linkTo(titleText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(saveButton.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .padding(top = 30.dp)
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(70.dp))

            LanguageCard(
                english
            ) {
                viewModel.onEvent(MainFlowEvent.LanguageClicked("GB"))

            }

            Spacer(modifier = Modifier.height(10.dp))

            LanguageCard(
                denmark
            ) {
                viewModel.onEvent(MainFlowEvent.LanguageClicked("DK"))

            }

            Spacer(modifier = Modifier.height(10.dp))

            LanguageCard(
                romanian
            ) {
                viewModel.onEvent(MainFlowEvent.LanguageClicked("RO"))
            }

            Spacer(modifier = Modifier.height(10.dp))

            LanguageCard(
                serbian
            ) {
                viewModel.onEvent(MainFlowEvent.LanguageClicked("RS"))

            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        Box(modifier = Modifier
            .constrainAs(saveButton) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bottomGuideline)
                width = Dimension.fillToConstraints
            }
            .padding(bottom = 30.dp)) {
            GradientButton(
                text = stringResource(R.string.btn_Save),
                onClick = {
                    viewModel.onEvent(MainFlowEvent.SaveLanguageClicked(""))
                    LocaleUtils.setLocale(context, viewModel.tempLanguage)
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun LanguageCard(
    language: LanguageDto,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .clickable { onClick() }
            .background(Color.White)
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text = localeToEmoji(language.flagCode))

        var name = ""

        when (language.countryCode) {
            "GB" -> name = stringResource(id = R.string.lang_english)
            "DK" -> name = stringResource(R.string.lang_denmark)
            "RO" -> name = stringResource(R.string.lang_romanian)
            "RS" -> name = stringResource(R.string.lang_serbian)
        }
        Text(
            text = name,
            style = h4bold,
            modifier = Modifier.padding(start = 20.dp)
        )

        Spacer(
            Modifier
                .weight(1f)
                .background(Color.Red)
        )

        if (language.isSelected)
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_checked_today),
                contentDescription = "Navigation Icon",
                tint = ProgressColor
            )
    }
}
