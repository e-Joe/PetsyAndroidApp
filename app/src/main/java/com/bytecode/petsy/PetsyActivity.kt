package com.bytecode.petsy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.bytecode.petsy.ui.navigation.SetupNavGraph
import com.bytecode.petsy.ui.theme.PetsyTheme

/**
 * Main activity in Petsy application
 *
 * @author Ilija Vucetic
 */
class PetsyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetsyTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}