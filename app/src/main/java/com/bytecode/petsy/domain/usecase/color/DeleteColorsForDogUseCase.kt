package com.bytecode.petsy.domain.usecase.color

import androidx.annotation.VisibleForTesting
import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.brushing.BrushingTimeDto
import com.bytecode.petsy.data.model.dto.brushing.toBrushingTimeEntity
import com.bytecode.petsy.data.model.dto.dog.toDogEntity
import com.bytecode.petsy.data.repository.brushingtime.BrushingTimeRepository
import com.bytecode.petsy.data.repository.color.ColorRepository
import com.bytecode.petsy.data.repository.dog.DogRepository
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class DeleteColorsForDogUseCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: ColorRepository
) : LocalUseCase<DeleteColorsForDogUseCase.Params, Unit>() {

    data class Params(
        val dogId: Long
    )

    override suspend fun FlowCollector<Unit>.execute(params: Params) {
        val dogId = params.dogId
        repository.deleteColorsForDog(dogId)
        emit(Unit)
    }
}