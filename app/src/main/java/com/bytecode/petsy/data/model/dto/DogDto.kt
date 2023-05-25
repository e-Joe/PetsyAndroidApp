package com.bytecode.petsy.data.model.dto

import java.util.UUID

data class DogDto(val name: String, val playerID: UUID = UUID.randomUUID())
