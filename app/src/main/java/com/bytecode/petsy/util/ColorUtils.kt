package com.bytecode.petsy.util

import kotlin.random.Random

fun generateRandomHexColor(): String {
    val color = Random.nextInt(0x1000000)
    return String.format("#%06X", color)
}