package com.bytecode.petsy.domain.usecase.validation

import javax.inject.Inject

class ValidateSecondName @Inject constructor() {

    fun execute(secondName: String): ValidationResult {
        val secondNameValidationType = ValidationType.LAST_NAME

        if (secondName.length < 2) {
            return ValidationResult(
                successful = false,
                errorMessage = "The second name needs to consist of a least 2 characters",
                validationType = secondNameValidationType,
                validationSubType = ValidationSubtype.LENGTH
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}