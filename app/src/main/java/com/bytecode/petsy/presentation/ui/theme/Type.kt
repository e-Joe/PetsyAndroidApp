package com.bytecode.petsy.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bytecode.petsy.R

val PetsyDefaultFont = FontFamily(
    Font(R.font.jakartasans_regular)
)

val PetsyDefaultFontBold = FontFamily(
    Font(R.font.jakartasans_bold)
)

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = PetsyDefaultFontBold,
        fontSize = 30.sp,
        color = TextSecondary,
        fontWeight = FontWeight.Bold,
    ),

    h2 = TextStyle(
        fontFamily = PetsyDefaultFontBold,
        fontSize = 20.sp,
        color = TextSecondary,
        fontWeight = FontWeight.Bold,
    ),

    h4 = TextStyle(
        fontFamily = PetsyDefaultFont,
        color = TextSecondary,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
    )
)

val h4bold = TextStyle(
    fontFamily = PetsyDefaultFontBold,
    color = TextSecondary,
    fontSize = 16.sp,
    fontWeight = FontWeight.W700,
)

val inputHint: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFont,
            fontSize = 14.sp,
            color = TextSecondary,
            fontWeight = FontWeight.Normal
        )
    }

val inputHint2: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFont,
            fontSize = 14.sp,
            color = TextSecondary,
            fontWeight = FontWeight.Normal
        )
    }

val regular: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFont,
            fontSize = 12.sp,
            color = TextSecondary,
            fontWeight = FontWeight.Normal
        )
    }

val h4_link: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFont,
            color = TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }

val button_text: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFontBold,
            color = TextSecondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    }

val button_text_inverse: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFontBold,
            color = TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
    }

val button_gradient_text: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFont,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }

val button_primary_text: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFont,
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }

val input_field_error: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFont,
            color = InputTextErrorColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    }

val ticker_text: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFontBold,
            color = TextSecondary,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
    }

val paused_text: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFont,
            color = TextSecondary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 2.sp
        )
    }

val brushing_time_text: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFont,
            color = TextSecondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 2.sp
        )
    }

val paragraph_text: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFont,
            color = TextSecondary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 1.sp
        )
    }

val share_time_text: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFontBold,
            color = TextSecondary,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
    }

val brushingCardText: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFont,
            color = TextSecondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
        )
    }

val brushingCardTextBold: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFontBold,
            color = TextSecondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
    }

val brushingCardPercentageText: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFontBold,
            color = TextSecondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
    }


val brushingCardTimeText: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFont,
            color = TextSecondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }

val chartTitle: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFontBold,
            color = TextSecondary2,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }

val chartNavigationDates: TextStyle
    @Composable get() {
        return TextStyle(
            fontFamily = PetsyDefaultFontBold,
            color = TextSecondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }