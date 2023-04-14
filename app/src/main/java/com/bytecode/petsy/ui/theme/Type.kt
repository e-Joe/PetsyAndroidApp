package com.bytecode.petsy.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 30.sp,
        color = TextSecondary,
        fontWeight = FontWeight.Bold,
    ),

    h4 = TextStyle(
        fontFamily = FontFamily.Default,
        color = TextSecondary,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
    )
)

val regular: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 12.sp,
            color = TextSecondary,
            fontWeight = FontWeight.Normal
        )
    }

val h4_link: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = FontFamily.Default,
            color = TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }

val button_text: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = FontFamily.Default,
            color = TextSecondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    }

val button_gradient_text: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = FontFamily.Default,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }

val button_primary_text: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = FontFamily.Default,
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }