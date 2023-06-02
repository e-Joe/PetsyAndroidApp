package com.bytecode.petsy.domain.di

import android.annotation.SuppressLint
import com.bytecode.petsy.data.repository.user.UserRepository
import com.bytecode.petsy.domain.usecase.user.GetLoggedInUserUseCase
import com.bytecode.petsy.domain.usecase.user.GetUsersUseCase
import com.bytecode.petsy.domain.usecase.user.SaveUserUseCase
import com.bytecode.petsy.domain.usecase.user.SaveUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@SuppressLint("VisibleForTests")
@Module
@InstallIn(SingletonComponent::class)
class UserDomainModule {

    @Singleton
    @Provides
    fun provideSaveUser(repository: UserRepository): SaveUserUseCase {
        return SaveUserUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSaveUsers(repository: UserRepository): SaveUsersUseCase {
        return SaveUsersUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetUser(repository: UserRepository): GetLoggedInUserUseCase {
        return GetLoggedInUserUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetUsers(repository: UserRepository): GetUsersUseCase {
        return GetUsersUseCase(repository)
    }
}