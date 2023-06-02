package com.bytecode.petsy.domain.usecase.user

import androidx.annotation.VisibleForTesting
import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.user.UserDto
import com.bytecode.petsy.data.model.dto.user.toUserDto
import com.bytecode.petsy.data.repository.user.UserRepository
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class GetUserByEmailUseCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: UserRepository
) : LocalUseCase<String, UserDto>() {


    override suspend fun FlowCollector<UserDto>.execute(email: String) {
        val user = repository.getUserByEmail(email)

        user?.let {
            emit(it.toUserDto())
        } ?: run {
            emit(UserDto(-1, "", "", "", "", "", false))
        }

    }
}