package com.bytecode.petsy.presentation.ui.activities.welcome

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.bytecode.petsy.data.local.dao.LanguageDao
import com.bytecode.petsy.data.repository.language.LanguageRepository
import com.bytecode.petsy.domain.usecase.language.GetLanguageUseCase
import com.bytecode.petsy.presentation.ui.navigation.LoginFlowGraph
import com.bytecode.petsy.presentation.ui.theme.PetsyTheme
import com.bytecode.petsy.util.LocaleUtils
import com.google.android.gms.tasks.Tasks.call
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Main activity in Petsy application
 *
 * @author Ilija Vucetic
 */
@AndroidEntryPoint
class WelcomeActivity : ComponentActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    @Inject
    lateinit var getLanguageUseCase: GetLanguageUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
//        firebaseAnalytics.logEvent("Test") {
//            param("test", "test")
//        }

        lifecycleScope.launch {
            call(getLanguageUseCase(Unit)) {
                LocaleUtils.setLocale(baseContext,it.countryCode)
                setContent {
                    PetsyTheme {
                        LocaleUtils.setLocale(baseContext,it.countryCode)
                        val navController = rememberNavController()
                        LoginFlowGraph(navController = navController, registerViewModel = hiltViewModel())
                    }
                }
            }
        }


    }

    private suspend fun <T> call(
        callFlow: Flow<T>,
        completionHandler: (collect: T) -> Unit = {}
    ) {
        callFlow
            .catch { handleError(it) }
            .collect {
                completionHandler.invoke(it)
            }
    }

    open fun handleError(exception: Throwable) {}
}