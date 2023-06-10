package com.bytecode.petsy.data.model.dto.dog

import com.bytecode.petsy.data.model.local.dog.DogEntity

fun DogDto.toDogEntity() = DogEntity(
    name = name,
    ownerId = ownerId,
    color = color,
    lastBrushingDate = lastBrushingDate,
    lastBrushingPeriod = lastBrushingPeriod
)
fun DogDto.toDogEntityId() = DogEntity(
    id = id,
    name = name,
    ownerId = ownerId,
    color = color,
    lastBrushingDate = lastBrushingDate,
    lastBrushingPeriod = lastBrushingPeriod
)

fun DogEntity.toUserDto() = DogDto(
    id = id,
    name = name,
    ownerId = ownerId,
    color = color,
    lastBrushingDate = lastBrushingDate,
    lastBrushingPeriod = lastBrushingPeriod

)

fun List<DogEntity>.toDogDtoList() = map { it.toUserDto() }

fun List<DogDto>.toDogEntityList() = map { it.toDogEntity() }