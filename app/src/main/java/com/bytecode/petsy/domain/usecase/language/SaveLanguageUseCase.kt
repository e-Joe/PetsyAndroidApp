package com.bytecode.petsy.domain.usecase.language

import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.language.LanguageDto
import com.bytecode.petsy.data.model.dto.language.toLanguageEntity
import com.bytecode.petsy.data.repository.language.LanguageRepository
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class SaveLanguageUseCase @Inject constructor(
    internal val repository: LanguageRepository
) : LocalUseCase<

        SaveLanguageUseCase.Params, Unit>() {

    data class Params(
        val language: LanguageDto
    )

    override suspend fun FlowCollector<Unit>.execute(params: Params) {
        val dto = params.language
        repository.saveLanguage(dto.toLanguageEntity())
        emit(Unit)
    }
}