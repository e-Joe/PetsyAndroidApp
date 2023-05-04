package com.bytecode.petsy.domain.usecase.validation

import javax.inject.Inject

class ValidatePasswordUpperCase @Inject constructor() {

    fun execute(password: String): ValidationResult {
        val containsUpperCaseLetter = password.any { it.isUpperCase() }

        if (!containsUpperCaseLetter) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one uppercase.",
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}