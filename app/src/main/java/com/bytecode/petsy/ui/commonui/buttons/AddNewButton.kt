@file:Suppress("UNUSED_EXPRESSION")

package com.bytecode.petsy.ui.commonui.buttons

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
import com.bytecode.petsy.ui.theme.TextPrimary
import com.bytecode.petsy.ui.theme.button_text_inverse

@Composable
fun AddNewButton(
    modifier: Modifier,
    text: String = stringResource(R.string.register_add_new),
    showIcon: Boolean = true,
    onClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        if(showIcon) {
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
    AddNewButton(modifier = Modifier) {}
}