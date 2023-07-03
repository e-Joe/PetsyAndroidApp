package com.bytecode.petsy.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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
    suspend fun getLoggedInUserByCredentials(email: String, password: String): UserEntity?

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE ${UserEntity.COLUMN_EMAIL} = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(userEntity: UserEntity): Int

    @Delete
    fun deleteUser(userEntity: UserEntity): Int
}