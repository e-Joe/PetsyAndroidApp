package com.bytecode.petsy.domain.usecase.validation

import javax.inject.Inject

class ValidatePassword @Inject constructor() {

    fun execute(password: String): ValidationResult {
        val passwordValidationType = ValidationType.PASSWORD

        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of a least 8 characters",
                validationType = passwordValidationType,
                validationSubType = ValidationSubtype.LENGTH
            )
        }

        val containsLowerLetter = password.any { it.isLowerCase() }
        val containsUpperCaseLetter = password.any { it.isUpperCase() }
        val containsNumber = password.any { it.isDigit() }

        if (!containsNumber) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one digit",
                validationType = passwordValidationType,
                validationSubType = ValidationSubtype.NUMBER
            )
        }

        if (!containsLowerLetter) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one lowercase.",
                validationType = passwordValidationType,
                validationSubType = ValidationSubtype.UPPERCASE_LETTER

            )
        }

        if (!containsUpperCaseLetter) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one uppercase.",
                validationType = passwordValidationType,
                validationSubType = ValidationSubtype.LOWERCASE_LETTER
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}