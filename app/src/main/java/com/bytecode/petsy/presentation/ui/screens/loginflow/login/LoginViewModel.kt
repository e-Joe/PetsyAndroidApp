package com.bytecode.petsy.presentation.ui.screens.loginflow.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.bytecode.framework.base.MvvmViewModel
import com.bytecode.petsy.domain.usecase.validation.ValidateEmail
import com.bytecode.petsy.domain.usecase.validation.ValidatePassword
import com.bytecode.petsy.domain.usecase.welcome.SaveOnBoardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val saveOnBoarding: SaveOnBoardingUseCase,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
) : MvvmViewModel() {

    var state by mutableStateOf(LoginState())

    private val validationChannel = Channel<ValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()

    fun saveOnBoardingState(completed: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        val params = SaveOnBoardingUseCase.Params(completed)
        call(saveOnBoarding(params))
    }

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is LoginFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            is LoginFormEvent.Submit -> {
                submitData()
            }
        }
    }

    fun submitData() {
        var emailresult = validateEmail.execute(state.email)
        var passwordResult = validatePassword.execute(state.password)


        val hasError = listOf(
            emailresult,
            passwordResult
        ).any {
            it?.errorMessage != null
        }

        if (hasError) {
            state = state.copy(
                emailError = emailresult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return
        } else {
            state = state.copy(
                emailError = null,
                passwordError = null
            )
        }

        saveOnBoardingState(true)
        viewModelScope.launch {
            validationChannel.send(ValidationEvent.Success)
        }

    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}


data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
)

sealed class LoginFormEvent {
    data class EmailChanged(val email: String) : LoginFormEvent()
    data class PasswordChanged(val password: String) : LoginFormEvent()
    data class Submit(val done: String = "") : LoginFormEvent()
}