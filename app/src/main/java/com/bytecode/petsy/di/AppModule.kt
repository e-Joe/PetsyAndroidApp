package com.bytecode.petsy.di

import com.bytecode.petsy.presentation.PetsyApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(): PetsyApp {
        return PetsyApp()
    }

}