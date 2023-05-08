package com.bytecode.petsy.domain.usecase.validation

class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null,
    val validationType: ValidationType? = ValidationType.NOT_DEFINED,
    val validationSubType: ValidationSubtype? = ValidationSubtype.NOT_DEFINED
)


enum class ValidationType {
    EMAIL,
    PASSWORD,
    FIRST_NAME,
    LAST_NAME,
    COUNTRY,
    PHONE_NUMBER,
    NOT_DEFINED
}

enum class ValidationSubtype {
    LENGTH,
    NUMBER,
    UPPERCASE_LETTER,
    LOWERCASE_LETTER,
    NOT_DEFINED
}