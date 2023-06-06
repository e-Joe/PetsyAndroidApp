package com.bytecode.petsy.data.model.local.brushing

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity(tableName = BrushingTimeEntity.TABLE_NAME)
data class BrushingTimeEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = COLUMN_ID) val id: Long = 0,
    @ColumnInfo(name = COLUMN_DURATION) val duration: Long,
    @ColumnInfo(name = COLUMN_START_TIME) val startTime: ZonedDateTime,
    @ColumnInfo(name = COLUMN_END_TIME) val endTime: ZonedDateTime,
    @ColumnInfo(name = COLUMN_DOG_ID) val dogId: Long,
    @ColumnInfo(name = COLUMN_OWNER_ID) val ownerId: Long,
) {
    companion object {
        const val TABLE_NAME = "brushing"
        const val COLUMN_ID = "id"
        const val COLUMN_DURATION = "duration"
        const val COLUMN_START_TIME = "start_time"
        const val COLUMN_END_TIME = "end_time"
        const val COLUMN_DOG_ID = "dog_id"
        const val COLUMN_OWNER_ID = "owner_id"
    }
}