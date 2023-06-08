package com.bytecode.petsy.domain.usecase.dog

import androidx.annotation.VisibleForTesting
import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.dog.DogDto
import com.bytecode.petsy.data.model.dto.dog.toDogEntity
import com.bytecode.petsy.data.repository.dog.DogRepository
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class SaveDogUseCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: DogRepository
) : LocalUseCase<SaveDogUseCase.Params, Long>() {

    data class Params(
        val dog: DogDto
    )

    override suspend fun FlowCollector<Long>.execute(params: Params) {
        val dto = params.dog
        val a = repository.insertDogWithId(dto.toDogEntity())
        emit(a)
    }
}