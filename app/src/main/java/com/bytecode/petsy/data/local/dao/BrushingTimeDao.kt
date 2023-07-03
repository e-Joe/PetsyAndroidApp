package com.bytecode.petsy.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.bytecode.framework.room.dao.BaseDao
import com.bytecode.petsy.data.model.local.brushing.BrushingTimeEntity
import com.bytecode.petsy.data.model.local.dog.DogEntity

@Dao
interface BrushingTimeDao : BaseDao<BrushingTimeEntity> {

    @Query("SELECT * FROM ${BrushingTimeEntity.TABLE_NAME} WHERE ${BrushingTimeEntity.COLUMN_OWNER_ID} = :ownerId")
    suspend fun getTimesForUser(ownerId: Long): List<BrushingTimeEntity>

    @Query("SELECT * FROM ${BrushingTimeEntity.TABLE_NAME} WHERE ${BrushingTimeEntity.COLUMN_DOG_ID} = :dogId")
    suspend fun getTimesForDog(dogId: Long): List<BrushingTimeEntity>

    @Query("DELETE FROM ${BrushingTimeEntity.TABLE_NAME} WHERE ${BrushingTimeEntity.COLUMN_DOG_ID} = :dogId")
    suspend fun deleteTimesForDog(dogId: Long): Int
}