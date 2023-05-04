package com.bytecode.petsy.domain.usecase.validation

import javax.inject.Inject

class ValidatePasswordLength @Inject constructor() {

    fun execute(password: String): ValidationResult {

        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of a least 8 characters",
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}