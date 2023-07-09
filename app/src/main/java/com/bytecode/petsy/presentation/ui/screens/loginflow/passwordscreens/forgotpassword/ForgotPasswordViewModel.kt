package com.bytecode.petsy.presentation.ui.screens.loginflow.passwordscreens.forgotpassword

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.bytecode.framework.base.MvvmViewModel
import com.bytecode.petsy.data.local.dao.UserDao
import com.bytecode.petsy.data.model.dto.dog.DogDto
import com.bytecode.petsy.data.model.dto.user.UserDto
import com.bytecode.petsy.domain.usecase.user.GetUserByEmailUseCase
import com.bytecode.petsy.domain.usecase.user.UpdateUserUseCase
import com.bytecode.petsy.domain.usecase.validation.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePasswordDigit: ValidatePasswordDigit,
    private val validatePasswordLowerCase: ValidatePasswordLowerCase,
    private val validatePasswordLength: ValidatePasswordLength,
    private val validatePasswordUpperCase: ValidatePasswordUpperCase,
    private val getUserByEmailUseCase: GetUserByEmailUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : MvvmViewModel() {

    var state by mutableStateOf(ForgotPasswordState())
    private val _stateFlow = MutableStateFlow(state)
    val stateFlow: StateFlow<ForgotPasswordState> get() = _stateFlow

    private val validationChannel = Channel<ForgotPassValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()

    private var user: UserDto? = null

    fun onEvent(event: ForgotPasswordEvent) {
        when (event) {
            is ForgotPasswordEvent.EmailChanged -> {
                state = state.copy(email = event.email)
                validateEmail(false)
            }

            is ForgotPasswordEvent.PasswordChanged -> {
                state = state.copy(password = event.password)

                state =
                    state.copy(isPasswordDigitValid = validatePasswordDigit.execute(state.password).successful)
                state =
                    state.copy(isPasswordLength = validatePasswordLength.execute(state.password).successful)
                state =
                    state.copy(isPasswordLowerCaseValid = validatePasswordLowerCase.execute(state.password).successful)
                state =
                    state.copy(isPasswordUpperCaseValid = validatePasswordUpperCase.execute(state.password).successful)

                if (state.isPasswordDigitValid && state.isPasswordLength && state.isPasswordLowerCaseValid && state.isPasswordUpperCaseValid && state.isEmailValid)
                    state = state.copy(isPasswordChangeButtonEnabled = true)
                else
                    state = state.copy(isPasswordChangeButtonEnabled = false)

            }

            is ForgotPasswordEvent.Submit -> {
                validateEmail(true)
            }
        }
    }

    private fun getUserByEmail() = safeLaunch {
        call(getUserByEmailUseCase(state.email)) {
            user = it
            Log.d("UpdatePass", state.password + " " + user!!.password)

            if (it.id > -1) {
                updateUserPassword()
            } else {
                viewModelScope.launch {
                    state = state.copy(emailError = "Please check your email")
                    _stateFlow.value = state
                }
            }
        }
    }

    private fun updateUserPassword() = safeLaunch {

        user = user!!.copy(password = state.password)

        call(updateUserUseCase(UpdateUserUseCase.Params(user!!))) {
            Log.d("UpdatePass", state.password + " 1 " + user!!.password)
            viewModelScope.launch {
                state = state.copy(emailError = "")
                _stateFlow.value = state
                Log.d("UpdatePass", state.password + " " + user!!.password)
                state = state.copy(showPassDialog = true)
                _stateFlow.value = state
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
                    emailError = emailResult.errorMessage.toString(),
                    isEmailValid = false
                )
            else
                state.copy(
                    emailError = "",
                    isEmailValid = false
                )
            return
        } else {
            state = state.copy(
                emailError = "",
                isEmailValid = true
            )
        }

        if (sendSuccessEvent) {
            getUserByEmail()
        }

        if (state.isPasswordDigitValid && state.isPasswordLength && state.isPasswordLowerCaseValid && state.isPasswordUpperCaseValid && state.isEmailValid)
            state = state.copy(isPasswordChangeButtonEnabled = true)
        else
            state = state.copy(isPasswordChangeButtonEnabled = false)
    }
}

sealed class ForgotPassValidationEvent {
    object Success : ForgotPassValidationEvent()
    object Fail : ForgotPassValidationEvent()
}

sealed class ForgotPasswordEvent {
    data class EmailChanged(val email: String) : ForgotPasswordEvent()
    data class PasswordChanged(val password: String) : ForgotPasswordEvent()
    data class Submit(var a: String = "") : ForgotPasswordEvent()
}

data class ForgotPasswordState(
    val email: String = "",
    val password: String = "",
    val emailError: String = "",
    val isEmailValid: Boolean = false,
    val isPasswordDigitValid: Boolean = false,
    val isPasswordLength: Boolean = false,
    val isPasswordUpperCaseValid: Boolean = false,
    val isPasswordLowerCaseValid: Boolean = false,
    val isPasswordChangeButtonEnabled: Boolean = false,
    val showPassDialog: Boolean = false
)