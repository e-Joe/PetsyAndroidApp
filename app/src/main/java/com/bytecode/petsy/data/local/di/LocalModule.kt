package com.bytecode.petsy.data.local.di

import android.content.Context
import androidx.room.Room
import com.bytecode.petsy.BuildConfig
import com.bytecode.petsy.data.local.dao.BrushingTimeDao
import com.bytecode.petsy.data.local.dao.ColorDao
import com.bytecode.petsy.data.local.dao.DogDao
import com.bytecode.petsy.data.local.dao.UserDao
import com.bytecode.petsy.data.local.db.PetsyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

private const val DB_NAME = "petsy_db"

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    @Singleton
    @Named(value = DB_NAME)
    fun provideDatabaseName(): String {
        return BuildConfig.DB_NAME
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @Named(value = DB_NAME) dbname: String,
        @ApplicationContext context: Context
    ): PetsyDatabase {
        return Room.databaseBuilder(context, PetsyDatabase::class.java, dbname)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: PetsyDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideDogDao(appDatabase: PetsyDatabase): DogDao {
        return appDatabase.dogDao()
    }

    @Provides
    @Singleton
    fun provideColorDao(appDatabase: PetsyDatabase): ColorDao {
        return appDatabase.colorDao()
    }

    @Provides
    @Singleton
    fun provideBrushingTimeDao(appDatabase: PetsyDatabase): BrushingTimeDao {
        return appDatabase.brushingTimeDao()
    }
}