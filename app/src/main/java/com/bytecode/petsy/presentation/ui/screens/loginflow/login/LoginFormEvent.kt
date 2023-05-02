package com.bytecode.petsy.presentation.ui.screens.loginflow.login

sealed class LoginFormEvent {
    data class EmailChanged(val email: String) : LoginFormEvent()
    data class PasswordChanged(val password: String) : LoginFormEvent()
    data class Submit(val done: String = "") : LoginFormEvent()
}