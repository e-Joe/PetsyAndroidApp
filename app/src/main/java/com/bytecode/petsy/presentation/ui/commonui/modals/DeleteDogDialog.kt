package com.bytecode.petsy.presentation.ui.commonui.modals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bytecode.petsy.presentation.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.presentation.ui.theme.button_primary_text

@Composable
fun DeleteDogDialog(setShowDialog: (Boolean) -> Unit, deleteDog: () -> Unit) {

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
                            text = "Are you sure you want to\n" +
                                    "delete “Penny” as your pet?",
                            style = MaterialTheme.typography.h2,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "You will also delete their statistic.",
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(top = 5.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            TextButton(
                                modifier = Modifier
                                    .align(Alignment.Center),
                                contentPadding = PaddingValues(0.dp),
                                onClick = {
                                    setShowDialog(false)
                                }) {
                                Text(
                                    text = "Cancel",
                                    style = button_primary_text
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        Box(modifier = Modifier.weight(1f)) {
                            GradientButton(
                                text = "Delete",
                                onClick = {
                                    deleteDog()
                                    setShowDialog(false)
                                },
                                paddingStart = 0.dp,
                                paddingEnd = 0.dp
                            )
                        }


                    }


                }
            }
        }
    }
}