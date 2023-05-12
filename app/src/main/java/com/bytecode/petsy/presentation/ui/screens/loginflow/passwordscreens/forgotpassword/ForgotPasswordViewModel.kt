package com.bytecode.petsy.presentation.ui.screens.loginflow.passwordscreens.forgotpassword

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
class ForgotPasswordViewModel @Inject constructor(
    private val validateEmail: ValidateEmail
) : MvvmViewModel() {

    var state by mutableStateOf(ForgotPasswordState())

    private val validationChannel = Channel<ValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()

    fun onEvent(event: ForgotPasswordEvent) {
        when (event) {
            is ForgotPasswordEvent.EmailChanged -> {
                state = state.copy(email = event.email)
                validateEmail(false)
            }

            is ForgotPasswordEvent.Submit -> {
                validateEmail(true)
            }
        }
    }

    private fun validateEmail(sendSuccessEvent: Boolean = true) {
        val emailResult = validateEmail.execute(state.email)

        val hasError = listOf(
            emailResult
        ).any {
            it?.errorMessage != null
        }

        if (hasError) {
            state = if (sendSuccessEvent)
                state.copy(
                    emailError = emailResult.errorMessage,
                    isEmailValid = false
                )
            else
                state.copy(
                    isEmailValid = false
                )
            return
        } else {
            state = state.copy(
                emailError = null,
                isEmailValid = true
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

sealed class ForgotPasswordEvent {
    data class EmailChanged(val email: String) : ForgotPasswordEvent()
    data class Submit(var a: String = "") : ForgotPasswordEvent()
}

data class ForgotPasswordState(
    val email: String = "",
    val emailError: String? = null,
    val isEmailValid: Boolean = false
)