package com.bytecode.petsy.domain.usecase.validation

class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)