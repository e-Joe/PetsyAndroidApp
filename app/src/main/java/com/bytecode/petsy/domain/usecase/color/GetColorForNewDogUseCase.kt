package com.bytecode.petsy.domain.usecase.color

import androidx.annotation.VisibleForTesting
import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.color.ColorDto
import com.bytecode.petsy.data.repository.color.ColorRepository
import com.bytecode.petsy.util.Constants
import com.bytecode.petsy.util.generateRandomHexColor
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class GetColorForNewDogUseCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: ColorRepository
) : LocalUseCase<Long, ColorDto>() {


    override suspend fun FlowCollector<ColorDto>.execute(userId: Long) {
        val userColors = repository.getUsedColorsForUser(userId)
        val allColors = Constants.colors

        var newColor: ColorDto = if (userColors.isEmpty()) {
            ColorDto(value = allColors[0], ownerId = userId)
        } else {
            var usedColors = arrayListOf<String>()
            userColors.forEach {
                usedColors.add(it.value)
            }
            val notUsedColors = allColors.filter { !usedColors.contains(it) }

            if (notUsedColors.isEmpty()) {
                ColorDto(value = generateRandomHexColor(), ownerId = userId)
            } else {
                ColorDto(value = notUsedColors[0], ownerId = userId)
            }
        }
        emit(newColor)
    }
}