package com.bytecode.petsy.presentation.ui.commonui.headers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bytecode.petsy.R


/**
 * The HeaderOnboarding() function uses Jetpack Compose's Card composable to
 * define a header UI with white background, and elevation.
 * The Box and Image composable functions are used to center the logo image
 * within the Card."
 */
@Composable
fun HeaderOnboarding() {
    Card(
        shape = RoundedCornerShape(0.dp),
        backgroundColor = Color.White,
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_logo_small),
                contentDescription = "",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}