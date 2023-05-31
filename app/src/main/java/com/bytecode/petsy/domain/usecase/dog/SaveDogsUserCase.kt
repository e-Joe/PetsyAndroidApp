package com.bytecode.petsy.domain.usecase.dog

import androidx.annotation.VisibleForTesting
import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.dog.DogDto
import com.bytecode.petsy.data.model.dto.dog.toDogEntityList
import com.bytecode.petsy.data.model.dto.user.UserDto
import com.bytecode.petsy.data.model.dto.user.toUserEntity
import com.bytecode.petsy.data.model.dto.user.toUserEntityList
import com.bytecode.petsy.data.repository.dog.DogRepository
import com.bytecode.petsy.data.repository.user.UserRepository
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class SaveDogsUserCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: DogRepository
) : LocalUseCase<SaveDogsUserCase.Params, Unit>() {

    data class Params(
        val dogs: List<DogDto>
    )

    override suspend fun FlowCollector<Unit>.execute(params: Params) {
        val dogsDtoList = params.dogs
        repository.saveDogs(dogsDtoList.toDogEntityList())
        emit(Unit)
    }
}