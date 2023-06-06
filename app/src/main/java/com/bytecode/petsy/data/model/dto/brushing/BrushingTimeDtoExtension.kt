package com.bytecode.petsy.data.model.dto.brushing

import com.bytecode.petsy.data.model.local.brushing.BrushingTimeEntity

fun BrushingTimeDto.toBrushingTimeEntity() = BrushingTimeEntity(
    duration = duration,
    startTime = startDateTime,
    endTime = endDateTime,
    dogId = dogId,
    ownerId = ownerId
)

fun BrushingTimeEntity.toBrushingTimeDto() = BrushingTimeDto(
    id = id,
    duration = duration,
    startDateTime = startTime,
    endDateTime = endTime,
    dogId = dogId,
    ownerId = ownerId
)

fun List<BrushingTimeEntity>.toBrushingTimeDtoList() = map { it.toBrushingTimeDto() }

fun List<BrushingTimeDto>.toBrushingTimeEntityList() = map { it.toBrushingTimeEntity() }