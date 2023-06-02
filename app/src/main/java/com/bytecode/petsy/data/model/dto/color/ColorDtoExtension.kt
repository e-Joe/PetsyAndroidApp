package com.bytecode.petsy.data.model.dto.color

import com.bytecode.petsy.data.model.local.color.ColorEntity

fun ColorDto.toColorEntity() = ColorEntity(
    value = value,
    ownerId = ownerId,
    dogId = dogId
)

fun ColorEntity.toColorDto() = ColorDto(
    id = id,
    value = value,
    ownerId = ownerId,
    dogId = dogId
)

fun List<ColorEntity>.toColorDtoList() = map { it.toColorDto() }
fun List<ColorDto>.toColorEntityList() = map { it.toColorEntity() }