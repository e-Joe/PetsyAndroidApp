package com.bytecode.petsy.presentation.ui.commonui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.theme.regular

/**
 * Creates a Composable function that displays a password rule item with an image and text.
 * @param text The text to display for the password rule.
 * @author Ilija Vuceic
 */
@Composable
fun PasswordRule(text: String, isValid: Boolean = false) {
    Row {
        if (isValid)
            Image(
                painter = painterResource(id = R.drawable.ic_circle_check_valid),
                contentDescription = ""
            )
        else
            Image(
                painter = painterResource(id = R.drawable.ic_circle_check),
                contentDescription = ""
            )
        Text(
            text = text,
            style = regular,
            modifier = Modifier
                .padding(start = 10.dp)

        )
    }
}

@Preview
@Composable
fun PreviewPasswordRule() {
    Box {
        PasswordRule("8+ Characters")
    }
}