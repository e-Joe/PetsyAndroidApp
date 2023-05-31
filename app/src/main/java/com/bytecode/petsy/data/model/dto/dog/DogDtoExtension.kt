package com.bytecode.petsy.data.model.dto.dog

import com.bytecode.petsy.data.model.local.dog.DogEntity

fun DogDto.toDogEntity() = DogEntity(
    name = name,
    ownerId = ownerId
)

fun DogEntity.toUserDto() = DogDto(
    id = id,
    name = name,
    ownerId = ownerId
)

fun List<DogEntity>.toDogDtoList() = map { it.toUserDto() }

fun List<DogDto>.toDogEntityList() = map { it.toDogEntity() }