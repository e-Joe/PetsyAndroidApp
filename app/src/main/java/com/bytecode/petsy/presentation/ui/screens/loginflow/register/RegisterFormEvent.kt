package com.bytecode.petsy.presentation.ui.screens.loginflow.register

sealed class RegisterFormEvent {
    data class EmailChanged(val email: String) : RegisterFormEvent()
    data class PasswordChanged(val password: String) : RegisterFormEvent()
    data class Submit(val done: String = "") : RegisterFormEvent()
}