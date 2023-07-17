package com.bytecode.petsy.data.model.dto.language

import com.bytecode.petsy.data.model.local.language.LanguageEntity

fun LanguageDto.toLanguageEntity() = LanguageEntity(
    id = id,
    languageName = languageName,
    countryCode = countryCode,
    flagCode = flagCode
)

fun LanguageEntity.toLanguageDto() = LanguageDto(
    id = id,
    languageName = languageName,
    countryCode = countryCode,
    flagCode = flagCode,
    isSelected = true
)
