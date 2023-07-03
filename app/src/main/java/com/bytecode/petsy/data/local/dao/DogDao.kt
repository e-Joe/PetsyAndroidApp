package com.bytecode.petsy.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bytecode.framework.room.dao.BaseDao
import com.bytecode.petsy.data.model.local.brushing.BrushingTimeEntity
import com.bytecode.petsy.data.model.local.dog.DogEntity

@Dao
interface DogDao : BaseDao<DogEntity> {

    @Query("SELECT * FROM ${DogEntity.TABLE_NAME} WHERE ${DogEntity.COLUMN_OWNER_ID} = :ownerId ORDER BY ${DogEntity.COLUMN_LAST_BRUSHING_DATE} DESC")
    suspend fun getDogs(ownerId: Long): List<DogEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateDog(dogEntity: DogEntity): Int

    @Insert
    fun insertDog(dogEntity: DogEntity): Long

    @Delete
    fun deleteDog(dogEntity: DogEntity): Int

    @Query("DELETE FROM ${DogEntity.TABLE_NAME} WHERE ${DogEntity.COLUMN_OWNER_ID} = :ownerId")
    fun deleteDogs(ownerId: Long): Int
}