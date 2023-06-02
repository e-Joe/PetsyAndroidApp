package com.bytecode.petsy.util

object Constants {

    val colors = arrayListOf(
        "#FF0000",
        "#00FF00",
        "#0000FF",
        "#FFFF00",
        "#FF00FF",
        "#00FFFF",
        "#FFFFFF",
        "#000000"
    )

    object Preference {
        const val WELCOME_PREF_FILE_NAME = "welcome_pref"

        object Keys {
            const val ON_BOARDING_KEY = "on_boarding_completed"
        }
    }

}