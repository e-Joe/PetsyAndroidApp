package com.bytecode.petsy.presentation.ui.commonui.inputs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytecode.petsy.presentation.ui.theme.*

/**
 * Function is a composable function in Kotlin that creates a rounded input field with
 * an outlined border using Jetpack Compose, a modern UI toolkit for building Android apps.
 * It takes two parameters:
 *
 * @param modifier Modifier - A modifier that allows you to apply additional styling or
 * behavior to the input field. You can use this parameter to customize the appearance
 * or behavior of the input field in your UI.
 *
 * @param hint String - A string that specifies the hint or placeholder text for the input field.
 * This text will be displayed inside the input field when it is empty, providing a visual
 * indication of what type of input is expected.
 *
 * @author Ilija Vucetic
 */
@Composable
fun RoundedInput(
    modifier: Modifier,
    hint: String,
    endIcon: Painter? = null,
    isEnabled: Boolean = true,
    isPassword: Boolean = false,
    onValueChange: (String) -> Unit = {},
    isError: Boolean = false,
    errorMessage: String = "",
    text: String = ""
) {
    val textState = remember {
        mutableStateOf(text)
    }

    Column(modifier = modifier) {
        Box {
            var passwordVisible by rememberSaveable { mutableStateOf(false) }

            OutlinedTextField(
                value = textState.value,
                onValueChange = { textState.value = it; onValueChange(it) },
                shape = RoundedCornerShape(50.dp),
                textStyle = inputHint,
                placeholder = {
                    Text(text = hint)
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = Color.Black,
                    focusedBorderColor = OutlineBorder,
                    unfocusedBorderColor = OutlineBorder,
                    disabledBorderColor = OutlineBorder,
                    disabledTextColor = TextSecondary,
                    textColor = Color.Red,
                    disabledPlaceholderColor = TextSecondary.copy(0.5f),
                    placeholderColor = TextSecondary.copy(0.5f),
                    focusedLabelColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (endIcon != null) {
                        Icon(
                            painter = endIcon, contentDescription = ""
                        )
                    }

                    if (isPassword) {
                        val image = if (passwordVisible) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    }
                },
                enabled = isEnabled,
                label = { Text(text = hint) },
                visualTransformation = if (passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                isError = isError,
            )
        }

        if (isError) {
            Text(
                text = errorMessage,
                style = input_field_error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }

}


@Preview
@Composable
fun PreviewRoundedInput() {
    Column {
        RoundedInput(
            modifier = Modifier.padding(top = 34.dp, start = 20.dp, end = 20.dp),
            hint = "Hint",
            endIcon = null
        )
    }
}