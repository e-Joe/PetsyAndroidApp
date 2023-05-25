package com.bytecode.petsy.presentation.ui.screens.loginflow.passwordscreens.resetpassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.bytecode.framework.base.MvvmViewModel
import com.bytecode.petsy.domain.usecase.validation.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validatePasswordDigit: ValidatePasswordDigit,
    private val validatePasswordLowerCase: ValidatePasswordLowerCase,
    private val validatePasswordLength: ValidatePasswordLength,
    private val validatePasswordUpperCase: ValidatePasswordUpperCase
) : MvvmViewModel() {

    var state by mutableStateOf(ResetPasswordState())

    private val validationChannel = Channel<ValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()

    fun onEvent(event: ResetPasswordEvent) {
        when (event) {
            is ResetPasswordEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
                state =
                    state.copy(isPasswordDigitValid = validatePasswordDigit.execute(state.password).successful)
                state =
                    state.copy(isPasswordLength = validatePasswordLength.execute(state.password).successful)
                state =
                    state.copy(isPasswordLowerCaseValid = validatePasswordLowerCase.execute(state.password).successful)
                state =
                    state.copy(isPasswordUpperCaseValid = validatePasswordUpperCase.execute(state.password).successful)

                validatePassword(false)
            }

            is ResetPasswordEvent.Submit -> {
                validatePassword(true)
            }
        }
    }

    private fun validatePassword(sendSuccessEvent: Boolean = true) {
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(
            passwordResult
        ).any {
            it?.errorMessage != null
        }

        if (hasError) {
            state = if (sendSuccessEvent)
                state.copy(
                    passwordError = passwordResult.errorMessage,
                    isPasswordValid = false
                )
            else
                state.copy(
                    isPasswordValid = false
                )
            return
        } else {
            state = state.copy(
                passwordError = null,
                isPasswordValid = true
            )
        }

        if (sendSuccessEvent)
            viewModelScope.launch {
                validationChannel.send(ValidationEvent.Success)
            }
    }
}

sealed class ValidationEvent {
    object Success : ValidationEvent()
    object Fail : ValidationEvent()
}

sealed class ResetPasswordEvent {
    data class PasswordChanged(val password: String) : ResetPasswordEvent()
    data class Submit(var a: String = "") : ResetPasswordEvent()
}

data class ResetPasswordState(
    val password: String = "",
    val passwordError: String? = null,
    val isPasswordValid: Boolean = false,
    val isPasswordDigitValid: Boolean = false,
    val isPasswordLength: Boolean = false,
    val isPasswordUpperCaseValid: Boolean = false,
    val isPasswordLowerCaseValid: Boolean = false
)