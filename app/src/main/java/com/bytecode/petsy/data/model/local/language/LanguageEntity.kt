package com.bytecode.petsy.data.model.local.language

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = LanguageEntity.TABLE_NAME)
data class LanguageEntity(
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = COLUMN_ID) val id: Long = 0,
    @ColumnInfo(name = COLUMN_NAME) val languageName: String,
    @ColumnInfo(name = COLUMN_COUNTRY_CODE) val countryCode: String,
    @ColumnInfo(name = COLUMN_FLAG_CODE) val flagCode: String
) {
    companion object {
        const val TABLE_NAME = "language"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "lang_name"
        const val COLUMN_COUNTRY_CODE = "country_code"
        const val COLUMN_FLAG_CODE = "flag_code"
    }
}