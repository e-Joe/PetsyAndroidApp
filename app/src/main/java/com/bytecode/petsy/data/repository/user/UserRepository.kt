package com.bytecode.petsy.data.repository.user

import androidx.annotation.VisibleForTesting
import com.bytecode.petsy.data.local.dao.UserDao
import com.bytecode.petsy.data.model.local.user.UserEntity
import javax.inject.Inject

class UserRepository
@Inject
constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val dao: UserDao
) {
    suspend fun saveUser(userEntity: UserEntity) = dao.insert(userEntity)

    suspend fun saveUsers(users: List<UserEntity>) = dao.insert(users)

    suspend fun getUsers() = dao.getUsers()

    suspend fun getLoggedInUser() = dao.getLoggedInUser()
}