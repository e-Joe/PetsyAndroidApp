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

class SaveBrushingTimeUseCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: BrushingTimeRepository
) : LocalUseCase<SaveBrushingTimeUseCase.Params, Unit>() {

    data class Params(
        val brushingTimeDto: BrushingTimeDto
    )

    override suspend fun FlowCollector<Unit>.execute(params: Params) {
        val dto = params.brushingTimeDto
        repository.saveBrushingTime(dto.toBrushingTimeEntity())
        emit(Unit)
    }
}