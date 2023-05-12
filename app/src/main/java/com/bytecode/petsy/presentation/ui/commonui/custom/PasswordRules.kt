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
fun PasswordRules(
    modifier: Modifier,
    isLengthRuleValid: Boolean = false,
    isDigitRuleValid: Boolean = false,
    isLoweCaseRuleValid: Boolean = false,
    isUpperCaseRuleValid: Boolean = false,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            PasswordRule(stringResource(R.string.password_characters_rule), isLengthRuleValid)
            Spacer(modifier = Modifier.height(10.dp))
            PasswordRule(stringResource(R.string.password_number_rule), isDigitRuleValid)
        }
        Column {
            PasswordRule(stringResource(R.string.password_uppercase_rule), isUpperCaseRuleValid)
            Spacer(modifier = Modifier.height(10.dp))
            PasswordRule(stringResource(R.string.password_lowercase_rule), isLoweCaseRuleValid)
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