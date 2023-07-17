package com.bytecode.petsy.data.model.dto.language

data class LanguageDto(
    val id: Long = 0,
    var languageName: String = "English",
    var countryCode: String = "GB",
    var flagCode: String = "GB",
    var isSelected: Boolean = false
)