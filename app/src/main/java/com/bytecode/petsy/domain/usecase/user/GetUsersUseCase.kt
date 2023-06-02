package com.bytecode.petsy.domain.usecase.user

import androidx.annotation.VisibleForTesting
import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.user.UserDto
import com.bytecode.petsy.data.model.dto.user.toUserDtoList
import com.bytecode.petsy.data.repository.user.UserRepository
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: UserRepository
) : LocalUseCase<Unit, List<UserDto>>() {


    override suspend fun FlowCollector<List<UserDto>>.execute(params: Unit) {
        val users = repository.getUsers()
        emit(users.toUserDtoList())
    }
}