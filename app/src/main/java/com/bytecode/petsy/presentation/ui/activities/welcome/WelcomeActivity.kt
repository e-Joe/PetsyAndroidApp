package com.bytecode.petsy.presentation.ui.activities.welcome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.bytecode.petsy.presentation.ui.navigation.LoginFlowGraph
import com.bytecode.petsy.presentation.ui.theme.PetsyTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity in Petsy application
 *
 * @author Ilija Vucetic
 */
@AndroidEntryPoint
class WelcomeActivity : ComponentActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent("Test") {
            param("test", "test")
        }
        setContent {
            PetsyTheme {
                val navController = rememberNavController()
                LoginFlowGraph(navController = navController, registerViewModel = hiltViewModel())
            }
        }
    }
}