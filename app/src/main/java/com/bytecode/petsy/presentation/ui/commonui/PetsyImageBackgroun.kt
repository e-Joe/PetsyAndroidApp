package com.bytecode.petsy.presentation.ui.commonui

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.bytecode.petsy.R

/**
 * Creates a Composable function that displays an Image composable with a background image resource.
 *
 * @author Ilija Vucetic
 **/
@Composable
fun PetsyImageBackground() {
    Image(
        painter = painterResource(id = R.drawable.bck_main),
        contentDescription = "",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}