package com.bytecode.petsy.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bytecode.petsy.data.local.dao.DogDao
import com.bytecode.petsy.data.local.dao.UserDao
import com.bytecode.petsy.data.model.local.dog.DogEntity
import com.bytecode.petsy.data.model.local.user.UserEntity

@Database(
    entities = [UserEntity::class, DogEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PetsyDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun dogDao(): DogDao
}