package com.bytecode.petsy.data.model.local.color

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ColorEntity.TABLE_NAME)
data class ColorEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = ColorEntity.COLUMN_ID) val id: Long = 0,
    @ColumnInfo(name = ColorEntity.COLUMN_VALUE) val value: String,
    @ColumnInfo(name = ColorEntity.COLUMN_OWNER_ID) val ownerId: Long,
    @ColumnInfo(name = ColorEntity.COLUMN_DOG_ID) val dogId: Long,
) {

    companion object {
        const val TABLE_NAME = "colors"
        const val COLUMN_ID = "id"
        const val COLUMN_VALUE = "value"
        const val COLUMN_OWNER_ID = "owner_id"
        const val COLUMN_DOG_ID = "dog_id"
    }
}