package com.bytecode.petsy.presentation.ui.screens.profileflow.mypets

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bytecode.petsy.R
import com.bytecode.petsy.data.model.dto.dog.DogDto
import com.bytecode.petsy.util.toColor


@Composable
fun DogItemMyPets(
    modifier: Modifier = Modifier, onClick: () -> Unit = {}, dog: DogDto
) {
    var background = Color.White.copy()
    var border = Color.Transparent
    var borderWidth = 0.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(53.dp),
        elevation = 3.dp,
        shape = RoundedCornerShape(size = 15.dp),
        backgroundColor = background,
        border = BorderStroke(width = borderWidth, color = border)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)

        ) {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                val (leftPart, deleteIcon) = createRefs()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .constrainAs(leftPart) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                        .padding(horizontal = 20.dp)
                        .background(Color.Transparent),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_paw),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(dog.color.toColor())
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = dog.name, style = MaterialTheme.typography.h2
                    )
                }

                Image(
                    modifier = Modifier
                        .constrainAs(deleteIcon) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                        .padding(end = 20.dp)
                        .clickable {
                            onClick()
                        },
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null
                )
            }
        }


    }
}