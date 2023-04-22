package com.bytecode.petsy.presentation.ui.commonui.custom

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytecode.petsy.R

/**
 * Creates a Composable function that displays a set of password rules.
 * @param modifier The [Modifier] to apply to the [Row] composable that contains the password rules.
 *
 * @author Ilija Vucetic
 */
@Composable
fun PasswordRules(modifier: Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            PasswordRule(stringResource(R.string.password_characters_rule))
            Spacer(modifier = Modifier.height(10.dp))
            PasswordRule(stringResource(R.string.password_number_rule))
        }
        Column {
            PasswordRule(stringResource(R.string.password_uppercase_rule))
            Spacer(modifier = Modifier.height(10.dp))
            PasswordRule(stringResource(R.string.password_lowercase_rule))
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