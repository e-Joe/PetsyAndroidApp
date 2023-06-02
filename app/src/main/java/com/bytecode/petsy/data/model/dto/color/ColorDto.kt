package com.bytecode.petsy.data.model.dto.color

data class ColorDto(
    val id: Long = System.currentTimeMillis(),
    val value: String = "",
    val ownerId: Long = -1,
    val dogId: Long = -1,
)
