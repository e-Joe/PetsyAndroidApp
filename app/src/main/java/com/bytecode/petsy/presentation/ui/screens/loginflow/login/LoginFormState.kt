package com.bytecode.petsy.presentation.ui.screens.loginflow.login

data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
)
