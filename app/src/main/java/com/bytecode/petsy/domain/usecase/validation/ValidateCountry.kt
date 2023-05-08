package com.bytecode.petsy.domain.usecase.validation

import javax.inject.Inject

class ValidateCountry @Inject constructor() {

    fun execute(country: String): ValidationResult {
        val countryValidationType = ValidationType.COUNTRY

        if (country.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please select country",
                validationType = countryValidationType,
                validationSubType = ValidationSubtype.LENGTH
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}