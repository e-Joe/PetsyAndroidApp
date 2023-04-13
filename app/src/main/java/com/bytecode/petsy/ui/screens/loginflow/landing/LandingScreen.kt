package com.bytecode.petsy.ui.screens.loginflow.landing

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bytecode.petsy.R
import com.bytecode.petsy.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.ui.theme.ButtonCollorGradient1
import com.bytecode.petsy.ui.theme.ButtonCollorGradient2
import com.bytecode.petsy.ui.theme.TextPrimary
import com.bytecode.petsy.ui.theme.TextSecondary
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun LandingScreen(navController: NavHostController) {
    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(
        Color.White
    )

    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            Image(
                painter = painterResource(id = R.drawable.bck_main),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Card(
                shape = RoundedCornerShape(0.dp),
                backgroundColor = Color.White,
                elevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_logo_small),
                        contentDescription = "",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            Text(
                text = "Welcome to\n" + "smart toothbrush ! \uD83D\uDC3E",
                fontSize = 30.sp,
                color = TextSecondary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 150.dp, start = 20.dp, end = 20.dp)

            )

            val context = LocalContext.current

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    GradientButton(
                        text = "Register",
                        textColor = Color.White,
                        gradient = Brush.horizontalGradient(
                            colors = listOf(
                                ButtonCollorGradient1,
                                ButtonCollorGradient2,
                                ButtonCollorGradient1
                            )
                        )
                    ) {}

                    TextButton(
                        modifier = Modifier.height(50.dp),
                        contentPadding = PaddingValues(0.dp),
                        onClick = {
                            Toast.makeText(context, "Login", Toast.LENGTH_SHORT).show()
                        }) {
                        Text(
                            text = "Login",
                            color = TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(70.dp))

                Row {
                    TextButton(
                        contentPadding = PaddingValues(0.dp),
                        onClick = {
                            Toast.makeText(context, "About us", Toast.LENGTH_SHORT).show()
                        }) {
                        Text(
                            text = "About us",
                            color = TextSecondary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Spacer(modifier = Modifier.width(40.dp))

                    TextButton(
                        contentPadding = PaddingValues(0.dp),
                        onClick = {
                            Toast.makeText(context, "Privacy policy", Toast.LENGTH_SHORT).show()
                        })
                    {
                        Text(
                            text = "Privacy policy",
                            color = TextSecondary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun LandingPreview() {
    LandingScreen(rememberNavController())
}