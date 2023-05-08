package com.bytecode.petsy.domain.usecase.validation

import javax.inject.Inject

class ValidateFirstName @Inject constructor() {

    fun execute(firstName: String): ValidationResult {
        val firstNameValidationType = ValidationType.FIRST_NAME

        if (firstName.length < 2) {
            return ValidationResult(
                successful = false,
                errorMessage = "The first name needs to consist of a least 2 characters",
                validationType = firstNameValidationType,
                validationSubType = ValidationSubtype.LENGTH
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}