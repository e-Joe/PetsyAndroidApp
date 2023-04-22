package com.bytecode.petsy.presentation.ui.activities.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.bytecode.petsy.presentation.ui.activities.welcome.WelcomeActivity
import com.bytecode.petsy.presentation.ui.activities.petsy.PetsyActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel by viewModels<SplashActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
        }
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            viewModel.startWelcome.collectLatest {
                delay(1000)
                if (it) navigateOnboardingActivity() else navigateMainActivity()
            }
        }

    }

    private fun navigateMainActivity() {
        val intent = Intent(this@SplashActivity, PetsyActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateOnboardingActivity() {
        val intent = Intent(this@SplashActivity, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}