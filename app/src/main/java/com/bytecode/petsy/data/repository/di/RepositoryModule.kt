package com.bytecode.petsy.data.repository.di

import android.annotation.SuppressLint
import android.content.Context
import com.bytecode.petsy.data.local.dao.DogDao
import com.bytecode.petsy.data.local.dao.UserDao
import com.bytecode.petsy.data.repository.dog.DogRepository
import com.bytecode.petsy.data.repository.user.UserRepository
import com.bytecode.petsy.data.repository.welcome.WelcomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@SuppressLint("VisibleForTests")
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideWelcomeRepository(
        @ApplicationContext context: Context
    ) = WelcomeRepository(context)

    @Singleton
    @Provides
    fun provideUserRepository(
        dao : UserDao
    ) = UserRepository(dao)

    @Singleton
    @Provides
    fun provideDogsRepository(
        dao : DogDao
    ) = DogRepository(dao)

}