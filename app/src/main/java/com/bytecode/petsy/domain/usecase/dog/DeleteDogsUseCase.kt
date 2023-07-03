package com.bytecode.petsy.domain.usecase.dog

import androidx.annotation.VisibleForTesting
import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.dog.DogDto
import com.bytecode.petsy.data.model.dto.dog.toDogEntityId
import com.bytecode.petsy.data.repository.dog.DogRepository
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class DeleteDogsUseCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: DogRepository
) : LocalUseCase<DeleteDogsUseCase.Params, Int>() {

    data class Params(
        val ownerId: Long
    )

    override suspend fun FlowCollector<Int>.execute(params: Params) {
        val ownerId = params.ownerId
        val a = repository.deleteDogs(ownerId)
        emit(a)
    }
}