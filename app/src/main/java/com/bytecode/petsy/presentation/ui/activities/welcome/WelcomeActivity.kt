package com.bytecode.petsy.presentation.ui.activities.welcome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.bytecode.petsy.presentation.ui.navigation.LoginFlowGraph
import com.bytecode.petsy.presentation.ui.theme.PetsyTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity in Petsy application
 *
 * @author Ilija Vucetic
 */
@AndroidEntryPoint
class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetsyTheme {
                val navController = rememberNavController()
                LoginFlowGraph(navController = navController)
            }
        }
    }
}