package com.bytecode.framework.room.dao

import androidx.room.TypeConverter
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

class ZonedDateTimeTypeConverter {
    @TypeConverter
    fun toZonedDateTime(value: Long?): ZonedDateTime? {
        return if (value == null) null else ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(value),
            ZoneOffset.systemDefault()
        )
    }

    @TypeConverter
    fun toLong(value: ZonedDateTime?): Long? {
        return if (value == null) null else value.toInstant().toEpochMilli()
    }
}
