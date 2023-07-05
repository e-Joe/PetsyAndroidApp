package com.bytecode.petsy.domain.usecase.brushingtime

import androidx.annotation.VisibleForTesting
import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.brushing.BrushingTimeDto
import com.bytecode.petsy.data.model.dto.brushing.toBrushingTimeEntity
import com.bytecode.petsy.data.model.dto.dog.toDogEntity
import com.bytecode.petsy.data.repository.brushingtime.BrushingTimeRepository
import com.bytecode.petsy.data.repository.dog.DogRepository
import kotlinx.coroutines.flow.FlowCollector
import java.time.ZonedDateTime
import javax.inject.Inject

class SaveTempBrushingTimesUseCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: BrushingTimeRepository
) : LocalUseCase<SaveTempBrushingTimesUseCase.Params, Unit>() {

    data class Params(
        val test: String = "a"
    )

    override suspend fun FlowCollector<Unit>.execute(params: Params) {
        val brushingTime1 = BrushingTimeDto(
            duration = 5,
            startDateTime = ZonedDateTime.now().minusDays(10),
            endDateTime = ZonedDateTime.now().minusDays(10).plusMinutes(30),
            ownerId = 1,
            dogId = 13
        )
        val brushingTime2 = BrushingTimeDto(
            duration = 52,
            startDateTime = ZonedDateTime.now().minusDays(10),
            endDateTime = ZonedDateTime.now().minusDays(10).plusMinutes(30),
            ownerId = 1,
            dogId = 13
        )
        val brushingTime3 = BrushingTimeDto(
            duration = 15,
            startDateTime = ZonedDateTime.now().minusDays(10),
            endDateTime = ZonedDateTime.now().minusDays(10).plusMinutes(30),
            ownerId = 1,
            dogId = 13
        )

        repository.saveBrushingTime(brushingTime1.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime2.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime3.toBrushingTimeEntity())
        emit(Unit)
    }
}