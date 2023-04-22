buildscript {
        val compose_ui_version by extra ("1.4.1")
}
plugins {
    id("com.google.devtools.ksp") version "1.8.20-1.0.11" apply false
}