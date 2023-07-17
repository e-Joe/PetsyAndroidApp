package com.bytecode.petsy.data.local.dao

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bytecode.framework.room.dao.BaseDao
import com.bytecode.petsy.data.model.local.language.LanguageEntity
import com.bytecode.petsy.data.model.local.user.UserEntity

@Dao
interface LanguageDao : BaseDao<LanguageEntity> {

    @Query("SELECT * FROM ${LanguageEntity.TABLE_NAME} WHERE ${LanguageEntity.COLUMN_ID} = :languageId")
    suspend fun getLanguage(languageId: Int): LanguageEntity?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(userEntity: UserEntity): Int

}