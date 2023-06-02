package com.bytecode.petsy.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.bytecode.framework.room.dao.BaseDao
import com.bytecode.petsy.data.model.local.color.ColorEntity
import com.bytecode.petsy.data.model.local.dog.DogEntity

@Dao
interface ColorDao : BaseDao<ColorEntity> {
    @Query("SELECT * FROM ${ColorEntity.TABLE_NAME} WHERE ${ColorEntity.COLUMN_OWNER_ID} = :ownerId")
    suspend fun getUsedColorsForUser(ownerId: Long): List<ColorEntity>
}