package com.bytecode.petsy.domain.di

import android.annotation.SuppressLint
import com.bytecode.petsy.domain.usecase.validation.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@SuppressLint("VisibleForTests")
@Module
@InstallIn(SingletonComponent::class)
class WelcomeModule {
    @Singleton
    @Provides
    fun provideEmailValidation(): ValidateEmail {
        return ValidateEmail()
    }

    @Singleton
    @Provides
    fun providePasswordValidation(): ValidatePassword {
        return ValidatePassword()
    }

    @Singleton
    @Provides
    fun providePasswordLengthValidation(): ValidatePasswordLength {
        return ValidatePasswordLength()
    }

    @Singleton
    @Provides
    fun providePasswordDigitValidation(): ValidatePasswordDigit {
        return ValidatePasswordDigit()
    }

    @Singleton
    @Provides
    fun providePasswordLowerCaseValidation(): ValidatePasswordLowerCase {
        return ValidatePasswordLowerCase()
    }

    @Singleton
    @Provides
    fun providePasswordUperCaseValidation(): ValidatePasswordUpperCase {
        return ValidatePasswordUpperCase()
    }
}