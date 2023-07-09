package com.bytecode.petsy.presentation.ui.screens.loginflow.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.bytecode.framework.base.MvvmViewModel
import com.bytecode.petsy.data.model.dto.user.UserDto
import com.bytecode.petsy.domain.usecase.user.GetLoggedInUserByCredentialsUseCase
import com.bytecode.petsy.domain.usecase.user.SaveUserUseCase
import com.bytecode.petsy.domain.usecase.validation.ValidateEmail
import com.bytecode.petsy.domain.usecase.validation.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val getLoggedInUserByCredentialsUseCase: GetLoggedInUserByCredentialsUseCase,
    private val saveUserUserCase: SaveUserUseCase,
) : MvvmViewModel() {

    var state by mutableStateOf(LoginState())
    private lateinit var user: UserDto

    private val validationChannel = Channel<ValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()

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

    private fun saveUser() = safeLaunch {
        val user = user.copy(isLoggedIn = true)
        val params = SaveUserUseCase.Params(user)
        call(saveUserUserCase(params))
        viewModelScope.launch {
            validationChannel.send(ValidationEvent.Success)
        }
    }

    private fun checkIfUserExist() = safeLaunch {
        call(getLoggedInUserByCredentialsUseCase(Pair(state.email, state.password))) {
            if (it.id > -1) {
                user = it
                saveUser()
            } else {
                viewModelScope.launch {
                    validationChannel.send(ValidationEvent.Fail)
                }
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
        checkIfUserExist()
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()

        object Fail : ValidationEvent()
    }
}


data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val showError: Boolean = false
)

sealed class LoginFormEvent {
    data class EmailChanged(val email: String) : LoginFormEvent()
    data class PasswordChanged(val password: String) : LoginFormEvent()
    data class Submit(val done: String = "") : LoginFormEvent()
}