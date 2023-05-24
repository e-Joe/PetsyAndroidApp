package com.bytecode.petsy.presentation.ui.activities.petsy

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowScreen
import com.bytecode.petsy.presentation.ui.theme.PetsyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetsyActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            changeColorStatusBar()
            PetsyTheme {
                MainFlowScreen()
            }
        }
    }

    @Composable
    private fun changeColorStatusBar() {
        val window: Window = window
        val decorView = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)
        wic.isAppearanceLightStatusBars = true
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
    }
}