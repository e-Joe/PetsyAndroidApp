buildscript {
        val compose_ui_version by extra ("1.4.1")
}
plugins {
    id("com.google.devtools.ksp") version "1.8.20-1.0.11" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
    id("com.google.firebase.crashlytics") version "2.9.6" apply false

//    id("com.android.library")
//    id("org.jetbrains.kotlin.android")
}

