package com.bytecode.petsy.ui.commonui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bytecode.petsy.R
import com.bytecode.petsy.ui.theme.TextSecondary

@Composable
fun PasswordRule(text: String) {
    Row {
        Image(painter = painterResource(id = R.drawable.ic_circle_check), contentDescription = "")
        Text(
            text = text,
            fontSize = 12.sp,
            color = TextSecondary,
            fontWeight = FontWeight.Normal,
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