@file:Suppress("UNUSED_EXPRESSION")

package com.bytecode.petsy.ui.commonui.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GradientButton(
    text: String,
    textColor: Color,
    gradient: Brush,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier.height(50.dp).fillMaxWidth().padding(start = 20.dp, end = 20.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(50),
        onClick = { onClick },
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        )
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(50.dp).fillMaxWidth()
                .background(gradient)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = text, color = textColor, fontSize = 18.sp,  fontWeight = FontWeight.Bold,)
        }
    }
}

@Preview
@Composable
fun GradientButtonPreview() {
    GradientButton(
        text = "button",
        textColor = Color.White,
        gradient = Brush.horizontalGradient(
            colors = listOf(Color.Yellow, Color.Green)
        )
    ) {}
}