package com.bytecode.petsy.domain.usecase.brushingtime

import androidx.annotation.VisibleForTesting
import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.brushing.BrushingTimeDto
import com.bytecode.petsy.data.model.dto.brushing.toBrushingTimeEntity
import com.bytecode.petsy.data.model.dto.dog.toDogEntity
import com.bytecode.petsy.data.repository.brushingtime.BrushingTimeRepository
import com.bytecode.petsy.data.repository.dog.DogRepository
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class DeleteTimeForDogUseCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: BrushingTimeRepository
) : LocalUseCase<DeleteTimeForDogUseCase.Params, Unit>() {

    data class Params(
        val dogId: Long
    )

    override suspend fun FlowCollector<Unit>.execute(params: Params) {
        val dogId = params.dogId
        repository.deleteTimesForDog(dogId)
        emit(Unit)
    }
}