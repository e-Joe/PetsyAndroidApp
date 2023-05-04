package com.bytecode.petsy.presentation.ui.screens.loginflow.register

data class RegisterFormState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isPasswordDigitValid : Boolean = false,
    val isPasswordLength : Boolean = false,
    val isPasswordUpperCaseValid : Boolean = false,
    val isPasswordLowerCaseValid : Boolean = false,
)
