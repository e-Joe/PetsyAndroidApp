package com.bytecode.petsy.data.model.dto.brushing

import java.time.ZonedDateTime

data class BrushingTimeDto(
    val id: Long = System.currentTimeMillis(),
    val duration: Long,
    val startDateTime: ZonedDateTime,
    val endDateTime: ZonedDateTime,
    val dogId: Long,
    val ownerId: Long
)
