package com.bytecode.petsy.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bytecode.framework.room.dao.ZonedDateTimeTypeConverter
import com.bytecode.petsy.data.local.dao.BrushingTimeDao
import com.bytecode.petsy.data.local.dao.ColorDao
import com.bytecode.petsy.data.local.dao.DogDao
import com.bytecode.petsy.data.local.dao.UserDao
import com.bytecode.petsy.data.model.local.brushing.BrushingTimeEntity
import com.bytecode.petsy.data.model.local.color.ColorEntity
import com.bytecode.petsy.data.model.local.dog.DogEntity
import com.bytecode.petsy.data.model.local.user.UserEntity

@Database(
    entities = [UserEntity::class, DogEntity::class, ColorEntity::class, BrushingTimeEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ZonedDateTimeTypeConverter::class)
abstract class PetsyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun dogDao(): DogDao
    abstract fun colorDao(): ColorDao
    abstract fun brushingTimeDao(): BrushingTimeDao
}