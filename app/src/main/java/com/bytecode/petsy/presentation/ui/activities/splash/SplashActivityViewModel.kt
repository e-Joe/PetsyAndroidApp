package com.bytecode.petsy.presentation.ui.activities.splash

import com.bytecode.framework.base.MvvmViewModel
import com.bytecode.petsy.domain.usecase.user.GetLoggedInUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(
    private val getLoggedInUserUseCase: GetLoggedInUserUseCase
) : MvvmViewModel() {

    private val _startWelcome = MutableStateFlow(false)
    val startWelcome = _startWelcome.asStateFlow()

    init {
        readOnBoardingState()
    }

    private fun readOnBoardingState() = safeLaunch {
        call(getLoggedInUserUseCase(Unit)) { user ->
            _startWelcome.value = !user.isLoggedIn
        }
    }

}