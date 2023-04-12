package com.bytecode.petsy.ui.screens.userflow

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun UserFlow() {
    Scaffold { paddingValues ->
        Log.d("Padding values", "$paddingValues")
        Box {
            Text(
                text = "UserFlow",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}