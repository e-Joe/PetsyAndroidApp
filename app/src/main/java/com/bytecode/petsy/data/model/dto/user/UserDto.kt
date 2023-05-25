package com.bytecode.petsy.data.model.dto.user

data class UserDto(
    val id: Long = 0,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val country: String,
    val isLoggedIn: Boolean
)
