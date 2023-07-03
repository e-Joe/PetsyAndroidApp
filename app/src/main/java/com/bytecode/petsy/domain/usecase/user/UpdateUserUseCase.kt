package com.bytecode.petsy.domain.usecase.user

import androidx.annotation.VisibleForTesting
import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.dog.DogDto
import com.bytecode.petsy.data.model.dto.dog.toDogEntity
import com.bytecode.petsy.data.model.dto.dog.toDogEntityId
import com.bytecode.petsy.data.model.dto.user.UserDto
import com.bytecode.petsy.data.model.dto.user.toUserEntityId
import com.bytecode.petsy.data.repository.dog.DogRepository
import com.bytecode.petsy.data.repository.user.UserRepository
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: UserRepository
) : LocalUseCase<UpdateUserUseCase.Params, Int>() {

    data class Params(
        val userDto: UserDto
    )

    override suspend fun FlowCollector<Int>.execute(params: Params) {
        val dto = params.userDto
        val a = repository.updateUser(dto.toUserEntityId())
        emit(a)
    }
}