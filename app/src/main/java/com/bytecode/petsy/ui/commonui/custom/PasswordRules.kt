package com.bytecode.petsy.ui.commonui.custom

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PasswordRules(modifier: Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            PasswordRule("8+ Characters")
            Spacer(modifier = Modifier.height(10.dp))
            PasswordRule("Number")
        }
        Column {
            PasswordRule("Uppercase Letter")
            Spacer(modifier = Modifier.height(10.dp))
            PasswordRule("Lowercase letter")
        }
    }
}

@Preview
@Composable
fun PreviewPasswordRules() {
    Box {
        PasswordRules(modifier = Modifier.fillMaxWidth())
    }
}