package com.bytecode.petsy.presentation.ui.screens.loginflow.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.bytecode.framework.base.MvvmViewModel
import com.bytecode.petsy.domain.usecase.validation.*
import com.bytecode.petsy.domain.usecase.welcome.SaveOnBoardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val saveOnBoarding: SaveOnBoardingUseCase,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validatePasswordDigit: ValidatePasswordDigit,
    private val validatePasswordLowerCase: ValidatePasswordLowerCase,
    private val validatePasswordLength: ValidatePasswordLength,
    private val validatePasswordUpperCase: ValidatePasswordUpperCase
) : MvvmViewModel() {

    var state by mutableStateOf(RegisterFormState())

    private val validationChannel = Channel<ValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()

    fun saveOnBoardingState(completed: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        val params = SaveOnBoardingUseCase.Params(completed)
        call(saveOnBoarding(params))
    }

    fun onEvent(event: RegisterFormEvent) {
        when (event) {
            is RegisterFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is RegisterFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
                state =
                    state.copy(isPasswordDigitValid = validatePasswordDigit.execute(state.password).successful)
                state =
                    state.copy(isPasswordLength = validatePasswordLength.execute(state.password).successful)
                state =
                    state.copy(isPasswordLowerCaseValid = validatePasswordLowerCase.execute(state.password).successful)
                state =
                    state.copy(isPasswordUpperCaseValid = validatePasswordUpperCase.execute(state.password).successful)
            }

            is RegisterFormEvent.Submit -> {
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

        viewModelScope.launch {
            validationChannel.send(ValidationEvent.Success)
        }

    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}