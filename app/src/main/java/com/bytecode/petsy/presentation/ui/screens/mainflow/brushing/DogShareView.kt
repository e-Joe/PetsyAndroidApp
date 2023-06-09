package com.bytecode.petsy.presentation.ui.screens.mainflow.brushing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytecode.petsy.R
import com.bytecode.petsy.data.model.dto.dog.DogDto
import com.bytecode.petsy.presentation.ui.theme.share_time_text
import com.bytecode.petsy.util.toColor
import com.bytecode.petsy.util.toLighterColor


@Composable
fun DogShareView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    dog: DogDto,
    time: String
) {
    var background = Color.White.copy()
    var border = Color.Transparent
    var borderWidth = 0.dp

    if (dog.isSelected) {
        background = dog.color.toLighterColor()
        border = dog.color.toColor()
        borderWidth = 2.dp
    }

    Card(
        modifier = Modifier
            .height(107.dp)
            .width(192.dp)
            .clickable {
                onClick()
            },
        elevation = 3.dp,
        shape = RoundedCornerShape(size = 15.dp),
        backgroundColor = background,
        border = BorderStroke(width = borderWidth, color = border)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center,

            ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.background(Color.Transparent),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_paw),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(dog.color.toColor())
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = dog.name,
                        style = MaterialTheme.typography.h2
                    )
                }
                Text(
                    text = time,
                    style = share_time_text
                )
            }

        }


    }
}