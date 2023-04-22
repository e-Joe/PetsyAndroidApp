package com.bytecode.petsy.presentation.ui.commonui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.theme.ButtonColorGradient1
import com.bytecode.petsy.presentation.ui.theme.OutlineBorder

@Composable
fun CustomProgress(modifier: Modifier, step: Int = 1) {
    var activeColor = ButtonColorGradient1
    var inactiveColor = OutlineBorder

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        PawView(color = if (step >= 1) activeColor else inactiveColor, isActive = step >= 1)

        RectangleView(if (step > 1) activeColor else inactiveColor)

        PawView(color = if (step >= 2) activeColor else inactiveColor, isActive = step >= 2)

        RectangleView(if (step > 2) activeColor else inactiveColor)

        PawView(color = if (step >= 3) activeColor else inactiveColor, isActive = step >= 3)
    }
}

@Composable
private fun RowScope.RectangleView(color: Color) {
    Box(
        modifier = Modifier.Companion
            .weight(1f)
            .height(3.dp)
            .background(color)
    )
}

@Composable
private fun PawView(color: Color, isActive: Boolean) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.White)
            .size(if (isActive) 32.dp else 22.dp)
            .shadow(
                elevation = 0.5.dp,
                shape = CircleShape,
                ambientColor = Color.Black.copy(alpha = 0.1f)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_paw),
            contentDescription = "Paw",
            colorFilter = ColorFilter.tint(color),
            modifier = Modifier.align(Alignment.Center).size(if (isActive) 24.dp else 16.dp)
        )
    }
}

@Preview
@Composable
fun PreviewCustomProgress() {
    Box(
        modifier = Modifier
            .width(330.dp)
    ) {
        Column {
            CustomProgress(modifier = Modifier.fillMaxWidth())

            CustomProgress(modifier = Modifier.fillMaxWidth(), step = 2)

            CustomProgress(modifier = Modifier.fillMaxWidth(), step = 3)
        }
    }
}