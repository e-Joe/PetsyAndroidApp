package com.bytecode.petsy.data.model.dto.user

import com.bytecode.petsy.data.model.local.user.UserEntity

fun UserDto.toUserEntity() = UserEntity(
    id = id,
    email = email,
    password = password,
    firstName = firstName,
    lastName = lastName,
    country = country,
    isLoggedIn = isLoggedIn
)

fun UserEntity.toUserDto() = UserDto(
    id = id,
    email = email,
    password = password,
    firstName = firstName,
    lastName = lastName,
    country = country,
    isLoggedIn = isLoggedIn
)

fun List<UserEntity>.toUserDtoList() = map { it.toUserDto() }

fun List<UserDto>.toUserEntityList() = map { it.toUserEntity() }