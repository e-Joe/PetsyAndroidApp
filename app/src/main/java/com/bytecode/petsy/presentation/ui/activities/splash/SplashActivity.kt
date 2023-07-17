package com.bytecode.petsy.presentation.ui.activities.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.bytecode.petsy.domain.usecase.language.GetLanguageUseCase
import com.bytecode.petsy.presentation.ui.activities.petsy.PetsyActivity
import com.bytecode.petsy.presentation.ui.activities.welcome.WelcomeActivity
import com.bytecode.petsy.util.LocaleUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel by viewModels<SplashActivityViewModel>()

    @Inject
    lateinit var getLanguageUseCase: GetLanguageUseCase

    var lang = "GB"

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
        }
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            call(getLanguageUseCase(Unit)) {
                Log.d("Jezik", it.flagCode)
                lang = it.countryCode
                LocaleUtils.setLocale(applicationContext, it.countryCode)
            }
        }

        lifecycleScope.launch {
            viewModel.startWelcome.collectLatest {
                delay(200)
                if (it) navigateOnboardingActivity() else navigateMainActivity()
            }
        }
    }

    private fun navigateMainActivity() {
        val intent = Intent(this@SplashActivity, PetsyActivity::class.java)
        intent.putExtra("LANG", lang)
        startActivity(intent)
        finish()
    }


    private fun navigateOnboardingActivity() {
        val intent = Intent(this@SplashActivity, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
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