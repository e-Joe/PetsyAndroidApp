package com.bytecode.petsy.presentation.ui.commonui.modals

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.theme.OutlineBorder
import com.bytecode.petsy.presentation.ui.theme.TextSecondary
import com.bytecode.petsy.presentation.ui.theme.inputHint2
import com.bytecode.petsy.presentation.ui.theme.input_field_error

@Composable
fun AddDogDialog(dogs: List<String>, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit) {

    val txtFieldError = remember { mutableStateOf(false) }
    val txtField = remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth(0.92f)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Today’s brushing",
                            style = MaterialTheme.typography.h2,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = "",
                            modifier = Modifier
                                .width(10.dp)
                                .height(15.dp)
                                .clickable { setShowDialog(false) }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = txtField.value,
                        onValueChange = { it ->
                            txtField.value = it
                            if (txtFieldError.value)
                                txtFieldError.value = false
                        },
                        shape = RoundedCornerShape(50.dp),
                        textStyle = inputHint2,
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 35.dp)
                            .border(width = 1.dp, OutlineBorder, shape = RoundedCornerShape(50.dp)),
                        enabled = true,
                        placeholder = {
                            Text(text = "Your dog’s name")
                        }
                    )

                    if (txtFieldError.value) {
                        Text(
                            text = "Dog with name ${txtField.value} already exist.",
                            style = input_field_error,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    GradientButton(
                        text = "Save",
                        onClick = {
                            if (dogs.contains(txtField.value.lowercase().trim())) {
                                Log.d("Pas", "Postoji")
                                txtFieldError.value = true
                            } else {
                                Log.d("Pas", "Ne postoji")
                                txtFieldError.value = false
                                setValue(txtField.value.trim())
                                setShowDialog(false)
                            }
                        },
                        paddingStart = 0.dp,
                        paddingEnd = 0.dp
                    )
                }
            }
        }
    }
}