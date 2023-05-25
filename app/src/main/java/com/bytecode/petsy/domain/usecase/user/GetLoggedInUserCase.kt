package com.bytecode.petsy.domain.usecase.user

import androidx.annotation.VisibleForTesting
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bytecode.framework.network.DataState
import com.bytecode.framework.usecase.DataStateUseCase
import com.bytecode.framework.usecase.FlowPagingUseCase
import com.bytecode.framework.usecase.LocalUseCase
import com.bytecode.petsy.data.model.dto.user.UserDto
import com.bytecode.petsy.data.model.dto.user.toUserDto
import com.bytecode.petsy.data.model.dto.user.toUserDtoList
import com.bytecode.petsy.data.model.dto.user.toUserEntity
import com.bytecode.petsy.data.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class GetLoggedInUserCase @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: UserRepository
) : LocalUseCase<Unit, UserDto>() {


    override suspend fun FlowCollector<UserDto>.execute(params: Unit) {
        val user = repository.getLoggedInUser()

        user?.let {
            emit(it.toUserDto())
        } ?: run {
            emit(UserDto(-1, "", "", "", "", "", false))
        }

    }
}