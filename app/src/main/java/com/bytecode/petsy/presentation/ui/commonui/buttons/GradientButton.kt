@file:Suppress("UNUSED_EXPRESSION")

package com.bytecode.petsy.presentation.ui.commonui.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bytecode.petsy.presentation.ui.theme.ButtonColorGradient1
import com.bytecode.petsy.presentation.ui.theme.ButtonColorGradient2
import com.bytecode.petsy.presentation.ui.theme.button_gradient_text
import com.bytecode.petsy.R

/**
 * Creates a Composable function that displays a gradient button with text.
 *
 * @param text The text to be displayed on the button.
 * @param onClick The callback function to be invoked when the button is clicked.
 * @author Ilija Vucetic
 */
@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    alpha: Float = 1f,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    paddingStart: Dp = 20.dp,
    paddingEnd: Dp = 20.dp,
) {
    Button(
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(start = paddingStart, end = paddingEnd)
            .alpha(alpha),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(50),
        onClick = { onClick() },
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp, pressedElevation = 0.dp, disabledElevation = 0.dp
        ),
        enabled = enabled
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .paint(
                    painterResource(id = R.drawable.bck_button_gradient),
                    contentScale = ContentScale.FillHeight
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {

            Row {
                Text(
                    text = text, style = button_gradient_text
                )
            }

        }
    }
}

@Preview
@Composable
fun GradientButtonPreview() {
    GradientButton(
        text = "button",
        alpha = 1f,
        onClick = {}
    )
}

