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
        val brushingTime1_1 = BrushingTimeDto(
            duration = 5,
            startDateTime = ZonedDateTime.now(),
            endDateTime = ZonedDateTime.now(),
            ownerId = 1,
            dogId = 1
        )
        val brushingTime1_2 = BrushingTimeDto(
            duration = 52,
            startDateTime = ZonedDateTime.now().minusDays(1),
            endDateTime = ZonedDateTime.now().minusDays(1),
            ownerId = 1,
            dogId = 1
        )
        val brushingTime1_3 = BrushingTimeDto(
            duration = 15,
            startDateTime = ZonedDateTime.now().minusDays(2),
            endDateTime = ZonedDateTime.now().minusDays(2),
            ownerId = 1,
            dogId = 1
        )

        val brushingTime1_4 = BrushingTimeDto(
            duration = 15,
            startDateTime = ZonedDateTime.now().minusDays(3),
            endDateTime = ZonedDateTime.now().minusDays(3),
            ownerId = 1,
            dogId = 1
        )

        val brushingTime1_5 = BrushingTimeDto(
            duration = 15,
            startDateTime = ZonedDateTime.now().minusDays(4),
            endDateTime = ZonedDateTime.now().minusDays(4),
            ownerId = 1,
            dogId = 1
        )

        val brushingTime1_6 = BrushingTimeDto(
            duration = 15,
            startDateTime = ZonedDateTime.now().minusDays(5),
            endDateTime = ZonedDateTime.now().minusDays(5),
            ownerId = 1,
            dogId = 1
        )

        val brushingTime1_6_1= BrushingTimeDto(
            duration = 15,
            startDateTime = ZonedDateTime.now().minusDays(5),
            endDateTime = ZonedDateTime.now().minusDays(5),
            ownerId = 1,
            dogId = 1
        )

        val brushingTime1_7 = BrushingTimeDto(
            duration = 150,
            startDateTime = ZonedDateTime.now().minusDays(6),
            endDateTime = ZonedDateTime.now().minusDays(6),
            ownerId = 1,
            dogId = 1
        )


        val brushingTime1_8 = BrushingTimeDto(
            duration = 54,
            startDateTime = ZonedDateTime.now().minusDays(7),
            endDateTime = ZonedDateTime.now().minusDays(7),
            ownerId = 1,
            dogId = 1
        )
        val brushingTime1_9 = BrushingTimeDto(
            duration = 22,
            startDateTime = ZonedDateTime.now().minusDays(8),
            endDateTime = ZonedDateTime.now().minusDays(8),
            ownerId = 1,
            dogId = 1
        )
        val brushingTime1_9_1 = BrushingTimeDto(
            duration = 15,
            startDateTime = ZonedDateTime.now().minusDays(9),
            endDateTime = ZonedDateTime.now().minusDays(9),
            ownerId = 1,
            dogId = 1
        )

        val brushingTime1_10 = BrushingTimeDto(
            duration = 15,
            startDateTime = ZonedDateTime.now().minusDays(10),
            endDateTime = ZonedDateTime.now().minusDays(10),
            ownerId = 1,
            dogId = 1
        )

        val brushingTime1_11 = BrushingTimeDto(
            duration = 15,
            startDateTime = ZonedDateTime.now().minusDays(11),
            endDateTime = ZonedDateTime.now().minusDays(11),
            ownerId = 1,
            dogId = 1
        )

        val brushingTime1_12 = BrushingTimeDto(
            duration = 79,
            startDateTime = ZonedDateTime.now().minusDays(12),
            endDateTime = ZonedDateTime.now().minusDays(12),
            ownerId = 1,
            dogId = 1
        )

        val brushingTime1_13= BrushingTimeDto(
            duration = 15,
            startDateTime = ZonedDateTime.now().minusDays(13),
            endDateTime = ZonedDateTime.now().minusDays(13),
            ownerId = 1,
            dogId = 1
        )

        val brushingTime1_14 = BrushingTimeDto(
            duration = 150,
            startDateTime = ZonedDateTime.now().minusDays(14),
            endDateTime = ZonedDateTime.now().minusDays(14),
            ownerId = 1,
            dogId = 1
        )



        val brushingTime2_1 = BrushingTimeDto(
            duration = 115,
            startDateTime = ZonedDateTime.now(),
            endDateTime = ZonedDateTime.now(),
            ownerId = 1,
            dogId = 2
        )
        val brushingTime2_2 = BrushingTimeDto(
            duration = 89,
            startDateTime = ZonedDateTime.now().minusDays(1),
            endDateTime = ZonedDateTime.now().minusDays(1),
            ownerId = 1,
            dogId = 2
        )
        val brushingTime2_3 = BrushingTimeDto(
            duration = 34,
            startDateTime = ZonedDateTime.now().minusDays(2),
            endDateTime = ZonedDateTime.now().minusDays(2),
            ownerId = 1,
            dogId = 2
        )

        val brushingTime2_4 = BrushingTimeDto(
            duration = 77,
            startDateTime = ZonedDateTime.now().minusDays(3),
            endDateTime = ZonedDateTime.now().minusDays(3),
            ownerId = 1,
            dogId = 2
        )

        val brushingTime2_5 = BrushingTimeDto(
            duration = 24,
            startDateTime = ZonedDateTime.now().minusDays(4),
            endDateTime = ZonedDateTime.now().minusDays(4),
            ownerId = 1,
            dogId = 2
        )

        val brushingTime2_6 = BrushingTimeDto(
            duration = 34,
            startDateTime = ZonedDateTime.now().minusDays(5),
            endDateTime = ZonedDateTime.now().minusDays(5),
            ownerId = 1,
            dogId = 2
        )

        val brushingTime2_6_1= BrushingTimeDto(
            duration = 150,
            startDateTime = ZonedDateTime.now().minusDays(5),
            endDateTime = ZonedDateTime.now().minusDays(5),
            ownerId = 1,
            dogId = 2
        )

        val brushingTime2_7 = BrushingTimeDto(
            duration = 120,
            startDateTime = ZonedDateTime.now().minusDays(6),
            endDateTime = ZonedDateTime.now().minusDays(6),
            ownerId = 1,
            dogId = 2
        )

        repository.saveBrushingTime(brushingTime1_1.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime1_2.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime1_3.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime1_4.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime1_5.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime1_6.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime1_6_1.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime1_7.toBrushingTimeEntity())

        repository.saveBrushingTime(brushingTime1_8.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime1_9.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime1_9_1.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime1_10.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime1_11.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime1_12.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime1_13.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime1_14.toBrushingTimeEntity())

        repository.saveBrushingTime(brushingTime2_1.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime2_2.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime2_3.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime2_4.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime2_5.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime2_6.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime2_6_1.toBrushingTimeEntity())
        repository.saveBrushingTime(brushingTime2_7.toBrushingTimeEntity())


        emit(Unit)
    }
}