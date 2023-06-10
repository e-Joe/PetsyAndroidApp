package com.bytecode.petsy.data.model.dto.dog

import java.time.ZonedDateTime

data class DogDto(
    val id: Long = System.currentTimeMillis(),
    val name: String = "",
    val ownerId: Long = 0,
    val color: String = "",
    val lastBrushingDate: ZonedDateTime = ZonedDateTime.now().withYear(1970),
    val lastBrushingPeriod: Long = 0,
    var isSelected: Boolean = false,
    var isSelectedForChart: Boolean = false
)
