package com.bytecode.petsy.data.repository.user

import androidx.annotation.VisibleForTesting
import com.bytecode.petsy.data.local.dao.UserDao
import com.bytecode.petsy.data.model.local.dog.DogEntity
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
    suspend fun getLoggedInUserByCredentials(email: String, password: String) =
        dao.getLoggedInUserByCredentials(email, password)
    suspend fun getUserByEmail(email: String) = dao.getUserByEmail(email)
    fun updateUser(userEntity: UserEntity): Int = dao.updateUser(userEntity)
    fun deleteUser(userEntity: UserEntity): Int = dao.deleteUser(userEntity)
}