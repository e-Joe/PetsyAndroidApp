package com.bytecode.petsy.domain.usecase.validation

import javax.inject.Inject

class ValidatePasswordDigit @Inject constructor() {

    fun execute(password: String): ValidationResult {
        val containsNumber = password.any { it.isDigit() }

        if (!containsNumber) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one digit",
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}