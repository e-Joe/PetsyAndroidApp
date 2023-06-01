package com.bytecode.petsy.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.bytecode.framework.room.dao.BaseDao
import com.bytecode.petsy.data.model.local.user.UserEntity

@Dao
interface UserDao : BaseDao<UserEntity> {

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE ${UserEntity.COLUMN_ID} = :userId")
    suspend fun getUser(userId: Int): UserEntity?

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME}")
    suspend fun getUsers(): List<UserEntity>

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE ${UserEntity.COLUMN_IS_LOGED_IN} = 1")
    suspend fun getLoggedInUser(): UserEntity?

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE ${UserEntity.COLUMN_PASSWORD} = :password AND ${UserEntity.COLUMN_EMAIL}= :email")
    suspend fun getLoggedInUserByEmail(email: String, password:String): UserEntity?
}