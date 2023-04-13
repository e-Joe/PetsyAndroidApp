package com.bytecode.petsy.ui.commonui.inputs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytecode.petsy.ui.theme.OutlineBorder

@Composable
fun RoundedInput(modifier: Modifier, hint: String) {
    val textState = remember {
        mutableStateOf("")
    }

    Box(modifier = modifier)
    {
        OutlinedTextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            shape = RoundedCornerShape(50.dp),
            placeholder = {
                Text(text = hint)
            },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                cursorColor = Color.Black,
                focusedBorderColor = OutlineBorder,
                unfocusedBorderColor = OutlineBorder
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview
@Composable
fun PreviewRoundedInput() {
    Column {
        RoundedInput(
            modifier = Modifier
                .padding(top = 34.dp, start = 20.dp, end = 20.dp),
            hint = "Hint"
        )
    }
}