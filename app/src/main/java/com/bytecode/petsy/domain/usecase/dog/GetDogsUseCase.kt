package com.bytecode.petsy.domain.usecase.dog

import androidx.annotation.VisibleForTesting
import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.dog.DogDto
import com.bytecode.petsy.data.model.dto.dog.toDogDtoList
import com.bytecode.petsy.data.repository.dog.DogRepository
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class GetDogsUseCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: DogRepository
) : LocalUseCase<Long, List<DogDto>>() {


    override suspend fun FlowCollector<List<DogDto>>.execute(ownerId: Long) {
        val dogs = repository.getDogs(ownerId)
        emit(dogs.toDogDtoList())
    }
}