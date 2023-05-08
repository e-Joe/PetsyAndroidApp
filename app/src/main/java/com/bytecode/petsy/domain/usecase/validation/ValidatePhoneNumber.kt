package com.bytecode.petsy.domain.usecase.validation

import javax.inject.Inject

class ValidatePhoneNumber @Inject constructor() {

    fun execute(phoneNumber: String): ValidationResult {
        val countryValidationType = ValidationType.PHONE_NUMBER

        if (phoneNumber.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Phone number can't be empty",
                validationType = countryValidationType,
                validationSubType = ValidationSubtype.LENGTH
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}