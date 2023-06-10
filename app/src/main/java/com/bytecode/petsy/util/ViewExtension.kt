package com.bytecode.petsy.util

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt


inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier =
    composed {
        clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }
    }


fun String.toColor(): Color {
    Log.d("Colors", this)
    return Color(this.toColorInt())
}

fun String.toLighterColor(): Color {

    val factor: Double = 0.8
    val color = if (this.startsWith("#")) this.substring(1) else this

    // Convert the hexadecimal color to RGB components
    val red = color.substring(0, 2).toInt(16)
    val green = color.substring(2, 4).toInt(16)
    val blue = color.substring(4, 6).toInt(16)

    // Calculate the new lighter color
    val clampedFactor = factor.coerceIn(0.0, 1.0)
    val newRed = (red + (255 - red) * clampedFactor).toInt()
    val newGreen = (green + (255 - green) * clampedFactor).toInt()
    val newBlue = (blue + (255 - blue) * clampedFactor).toInt()

    // Convert the new RGB components to hexadecimal color
    val newHexColor = String.format("#%02X%02X%02X", newRed, newGreen, newBlue)


    return Color(newHexColor.toColorInt())
}









