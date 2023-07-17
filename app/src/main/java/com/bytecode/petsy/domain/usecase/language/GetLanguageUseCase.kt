package com.bytecode.petsy.domain.usecase.language

import android.util.Log
import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.language.LanguageDto
import com.bytecode.petsy.data.model.dto.language.toLanguageDto
import com.bytecode.petsy.data.repository.language.LanguageRepository
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class GetLanguageUseCase @Inject constructor(
    internal val repository: LanguageRepository
) : LocalUseCase<Unit, LanguageDto>() {

    override suspend fun FlowCollector<LanguageDto>.execute(params: Unit) {
        val user = repository.getLanguage(0)

        user?.let {
            Log.d("Jezik", "01 - " + it.flagCode)
            emit(it.toLanguageDto())
        } ?: run {
            Log.d("Jezik", "02 - ")
            emit(LanguageDto(0, "English", "GB", "GB", true))
        }
    }
}