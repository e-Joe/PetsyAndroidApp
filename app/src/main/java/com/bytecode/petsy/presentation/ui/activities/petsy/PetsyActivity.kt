package com.bytecode.petsy.presentation.ui.activities.petsy

import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
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

        var lang = "GB"
        lang = intent.getStringExtra("LANG").toString()
        Log.d("Testiranje", lang)
        setContent {
            changeColorStatusBar()
            PetsyTheme {
                MainFlowScreen(languageCode = lang)
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


