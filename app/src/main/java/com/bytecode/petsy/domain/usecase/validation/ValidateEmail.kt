package com.bytecode.petsy.domain.usecase.validation

import android.util.Patterns
import com.bytecode.framework.usecase.AsyncUseCase
import com.bytecode.framework.usecase.ReturnUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ValidateEmail @Inject constructor() {

    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "That's not a valid email"
            )

        }
        return ValidationResult(successful = true)
    }

}