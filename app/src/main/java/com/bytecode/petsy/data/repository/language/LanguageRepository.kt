package com.bytecode.petsy.data.repository.language

import com.bytecode.petsy.data.local.dao.LanguageDao
import com.bytecode.petsy.data.model.local.language.LanguageEntity
import javax.inject.Inject

class LanguageRepository
@Inject
constructor(
    internal val dao: LanguageDao
) {
    suspend fun saveLanguage(language: LanguageEntity) = dao.insert(language)

    suspend fun getLanguage(languageId: Int) = dao.getLanguage(languageId)
}