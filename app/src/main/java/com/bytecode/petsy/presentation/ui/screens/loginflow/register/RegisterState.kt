package com.bytecode.petsy.presentation.ui.screens.loginflow.register

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
)