package com.bytecode.petsy.domain.usecase.user

import androidx.annotation.VisibleForTesting
import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.user.UserDto
import com.bytecode.petsy.data.model.dto.user.toUserEntity
import com.bytecode.petsy.data.model.dto.user.toUserEntityList
import com.bytecode.petsy.data.repository.user.UserRepository
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class SaveUsersUserCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: UserRepository
) : LocalUseCase<SaveUsersUserCase.Params, Unit>() {

    data class Params(
        val users: List<UserDto>
    )

    override suspend fun FlowCollector<Unit>.execute(params: Params) {
        val usersDtoList = params.users
        repository.saveUsers(usersDtoList.toUserEntityList())
        emit(Unit)
    }
}