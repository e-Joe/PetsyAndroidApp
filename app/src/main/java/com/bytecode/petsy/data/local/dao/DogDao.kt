package com.bytecode.petsy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bytecode.framework.room.dao.BaseDao
import com.bytecode.petsy.data.model.local.dog.DogEntity

@Dao
interface DogDao : BaseDao<DogEntity> {

    @Query("SELECT * FROM ${DogEntity.TABLE_NAME} WHERE ${DogEntity.COLUMN_OWNER_ID} = :ownerId")
    suspend fun getDogs(ownerId: Long): List<DogEntity>

    @Insert
    fun insertDog(dogEntity: DogEntity): Long
}