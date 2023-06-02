package com.bytecode.petsy.domain.usecase.color

import androidx.annotation.VisibleForTesting
import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.color.ColorDto
import com.bytecode.petsy.data.model.dto.color.toColorEntityList
import com.bytecode.petsy.data.model.dto.dog.DogDto
import com.bytecode.petsy.data.model.dto.dog.toDogEntityList
import com.bytecode.petsy.data.repository.color.ColorRepository
import com.bytecode.petsy.data.repository.dog.DogRepository
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class SaveColorsUseCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: ColorRepository
) : LocalUseCase<SaveColorsUseCase.Params, Unit>() {

    data class Params(
        val colors: List<ColorDto>
    )

    override suspend fun FlowCollector<Unit>.execute(params: Params) {
        val colorsDtoList = params.colors
        repository.saveColors(colorsDtoList.toColorEntityList())
        emit(Unit)
    }
}