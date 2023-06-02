package com.bytecode.petsy.domain.usecase.color

import androidx.annotation.VisibleForTesting
import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.color.ColorDto
import com.bytecode.petsy.data.model.dto.color.toColorEntity
import com.bytecode.petsy.data.model.dto.dog.DogDto
import com.bytecode.petsy.data.model.dto.dog.toDogEntity
import com.bytecode.petsy.data.repository.color.ColorRepository
import com.bytecode.petsy.data.repository.dog.DogRepository
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class SaveColorUseCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: ColorRepository
) : LocalUseCase<SaveColorUseCase.Params, Unit>() {

    data class Params(
        val color: ColorDto
    )

    override suspend fun FlowCollector<Unit>.execute(params: Params) {
        val dto = params.color
        repository.saveColor(dto.toColorEntity())
        emit(Unit)
    }
}