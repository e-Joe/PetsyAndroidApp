package com.bytecode.petsy.domain.usecase.validation

import com.bytecode.framework.usecase.ReturnUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ValidatePassword @Inject constructor() {

    fun execute(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of a least 8 characters"
            )
        }

        val containsLetter = password.any { it.isLetter() }
        val containsNumber = password.any { it.isDigit() }

        if (!containsNumber) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one digit"
            )
        }

        if (!containsLetter) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one letter"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}