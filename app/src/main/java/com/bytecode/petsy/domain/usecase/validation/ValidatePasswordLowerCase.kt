package com.bytecode.petsy.domain.usecase.validation

import javax.inject.Inject

class ValidatePasswordLowerCase @Inject constructor() {

    fun execute(password: String): ValidationResult {
        val containsLowerLetter = password.any { it.isLowerCase() }


        if (!containsLowerLetter) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one lowercase.",

                )
        }

        return ValidationResult(
            successful = true
        )
    }

}