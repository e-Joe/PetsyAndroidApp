package com.bytecode.petsy.data.repository.color

import androidx.annotation.VisibleForTesting
import com.bytecode.petsy.data.local.dao.ColorDao
import com.bytecode.petsy.data.model.local.color.ColorEntity

import javax.inject.Inject

class ColorRepository
@Inject
constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val dao: ColorDao
) {
    suspend fun saveColor(colorEntity: ColorEntity) = dao.insert(colorEntity)

    suspend fun saveColors(colors: List<ColorEntity>) = dao.insert(colors)

    suspend fun getUsedColorsForUser(ownerId: Long) = dao.getUsedColorsForUser(ownerId)
}