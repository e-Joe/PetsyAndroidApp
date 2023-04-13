package com.bytecode.petsy.ui.screens.loginflow.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bytecode.petsy.R
import com.bytecode.petsy.ui.commonui.buttons.GradientButton
import com.bytecode.petsy.ui.commonui.inputs.RoundedInput
import com.bytecode.petsy.ui.theme.ButtonCollorGradient1
import com.bytecode.petsy.ui.theme.ButtonCollorGradient2
import com.bytecode.petsy.ui.theme.TextPrimary
import com.bytecode.petsy.ui.theme.TextSecondary

@Composable
fun RegisterScreen(navController: NavHostController) {
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

            Column(
                modifier = Modifier
                    .padding(top = 150.dp)
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_temp_steps),
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Text(
                    text = "Your account",
                    fontSize = 30.sp,
                    color = TextSecondary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 34.dp, start = 20.dp, end = 20.dp)

                )

                Spacer(modifier = Modifier.height(20.dp))

                RoundedInput(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                RoundedInput(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                )
            }


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
                        text = "Next",
                        textColor = Color.White,
                        gradient = Brush.horizontalGradient(
                            colors = listOf(
                                ButtonCollorGradient1,
                                ButtonCollorGradient2,
                                ButtonCollorGradient1
                            )
                        )
                    ) {}

                    Spacer(modifier = Modifier.height(40.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "You already have an account.",
                            color = TextSecondary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                        )


                        Text(
                            text = "Login Here",
                            color = TextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 3.dp),
                            textDecoration = TextDecoration.Underline
                        )
                    }

                }

                Spacer(modifier = Modifier.height(80.dp))

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
fun RegisterPreview() {
    RegisterScreen(rememberNavController())
}