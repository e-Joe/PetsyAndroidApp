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

class DeleteTimeForUserUseCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: BrushingTimeRepository
) : LocalUseCase<DeleteTimeForUserUseCase.Params, Unit>() {

    data class Params(
        val userId: Long
    )

    override suspend fun FlowCollector<Unit>.execute(params: Params) {
        val userId = params.userId
        repository.deleteTimesForUser(userId)
        emit(Unit)
    }
}