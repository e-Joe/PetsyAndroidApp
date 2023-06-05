package com.bytecode.petsy.data.model.dto.dog

data class DogDto(
    val id: Long = System.currentTimeMillis(),
    val name: String = "",
    val ownerId: Long = 0,
    val color: String = "",
    var isSelected: Boolean = false
)
