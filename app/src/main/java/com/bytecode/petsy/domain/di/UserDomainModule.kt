package com.bytecode.petsy.domain.di

import android.annotation.SuppressLint
import com.bytecode.petsy.data.repository.user.UserRepository
import com.bytecode.petsy.domain.usecase.user.GetLoggedInUserUseCase
import com.bytecode.petsy.domain.usecase.user.GetUsersUserCase
import com.bytecode.petsy.domain.usecase.user.SaveUserUserCase
import com.bytecode.petsy.domain.usecase.user.SaveUsersUserCase
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
    fun provideSaveUser(repository: UserRepository): SaveUserUserCase {
        return SaveUserUserCase(repository)
    }

    @Singleton
    @Provides
    fun provideSaveUsers(repository: UserRepository): SaveUsersUserCase {
        return SaveUsersUserCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetUser(repository: UserRepository): GetLoggedInUserUseCase {
        return GetLoggedInUserUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetUsers(repository: UserRepository): GetUsersUserCase {
        return GetUsersUserCase(repository)
    }
}