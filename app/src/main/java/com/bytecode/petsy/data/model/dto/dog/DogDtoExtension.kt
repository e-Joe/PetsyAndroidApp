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

fun DogDto.calculatePercentage(): Double {
    return this.lastBrushingPeriod.toDouble() / 120
}

fun DogDto.calculatePercentageRounded(): Int {
    val percentage = ((this.lastBrushingPeriod / 120.toDouble()) * 100).toInt()

    return if (percentage > 100)
        100
    else
        percentage
}