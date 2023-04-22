package com.bytecode.petsy.presentation.ui.activities.petsy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.bytecode.petsy.presentation.ui.theme.PetsyTheme

class PetsyActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetsyTheme {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red))
            }
        }
    }
}