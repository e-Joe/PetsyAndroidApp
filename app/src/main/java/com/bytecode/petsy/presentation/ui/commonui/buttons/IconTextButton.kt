@file:Suppress("UNUSED_EXPRESSION")

package com.bytecode.petsy.presentation.ui.commonui.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.theme.TextPrimary
import com.bytecode.petsy.presentation.ui.theme.button_text_inverse

@Composable
fun IconTextButton(
    modifier: Modifier,
    text: String = stringResource(R.string.register_add_new),
    showIcon: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(onClick = onClick)
    ) {
        if (showIcon) {
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "",
                tint = TextPrimary
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
        Text(
            text = text,
            style = button_text_inverse,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Preview
@Composable
fun AddNewButtonPreview() {
    IconTextButton(modifier = Modifier) {}
}