package com.bytecode.petsy.presentation

import android.app.Application
import android.content.Context
import com.bytecode.petsy.util.LocaleUtils
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PetsyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}