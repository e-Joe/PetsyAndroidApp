package com.bytecode.petsy.data.model.local.dog

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DogEntity.TABLE_NAME)
data class DogEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = DogEntity.COLUMN_ID) val id: Long = 0,
    @ColumnInfo(name = DogEntity.COLUMN_NAME) val name: String,
    @ColumnInfo(name = DogEntity.COLUMN_OWNER_ID) val ownerId: Long,
    @ColumnInfo(name = DogEntity.COLUMN_COLOR) val color: String,
) {

    companion object {
        const val TABLE_NAME = "dogs"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_OWNER_ID = "owner_id"
        const val COLUMN_COLOR = "color"
    }
}