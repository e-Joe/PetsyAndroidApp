import com.bytecode.buildsrc.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = Configs.Id
    compileSdk = Configs.CompileSdk

    defaultConfig {
        applicationId = "pet.petsie.app.android"
        minSdk = Configs.MinSdk
        targetSdk = Configs.TargetSdk
        versionCode = Configs.VersionCode
        versionName = Configs.VersionName
        compileSdk = compileSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        bundle {
            language {
                enableSplit = false
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"${Configs.Release.BaseUrl}\"")
            buildConfigField("String", "DB_NAME", "\"${Configs.Release.DbName}\"")
        }

        debug {
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"${Configs.Debug.BaseUrl}\"")
            buildConfigField("String", "DB_NAME", "\"${Configs.Debug.DbName}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(mapOf("path" to ":framework")))

    //Core
    implementation(SupportLib.CoreKtx)
    implementation(SupportLib.LifecycleRuntime)
    implementation(SupportLib.Splashscreen)

    //Compose
    implementation(ComposeLib.Ui)
    implementation(ComposeLib.Preview)
    implementation(ComposeLib.Material)
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation(ComposeLib.MaterialIconCore)
    implementation(ComposeLib.MaterialIconExtended)
    implementation("androidx.appcompat:appcompat:1.6.1")
    androidTestImplementation(AndroidTestingLib.ComposeTestJunit)
    debugImplementation(ComposeLib.Tooling)
    debugImplementation(ComposeLib.Manifest)
    implementation(ComposeLib.Activity)

    //Navigation
    implementation(NavigationLib.Navigation)
    implementation(AccompanistLib.Systemuicontroller)

    // Dagger Hilt
    implementation(DaggerHiltLib.Android)
    kapt(DaggerHiltLib.Compiler)
    implementation(DaggerHiltLib.Compose)

    // Storage
    implementation(StorageLib.RoomKtx)
    ksp(StorageLib.RoomCompiler)
    implementation(StorageLib.DatastorePref)
    implementation(StorageLib.Datastore)
    implementation(StorageLib.SecurityPref)

    //Test
    testImplementation(TestingLib.Junit)
    androidTestImplementation(AndroidTestingLib.JunitExt)
    androidTestImplementation(AndroidTestingLib.EspressoCore)

    implementation("com.github.hitanshu-dhawan:CircularProgressBar-Compose:1.0.0-rc01")

    implementation("com.google.code.gson:gson:2.8.7")

    implementation(ComposeLib.Paging)
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // Includes the core logic for charts and other elements.
    implementation("com.patrykandpatrick.vico:core:1.6.5")

    // For Jetpack Compose.
    implementation("com.patrykandpatrick.vico:compose:1.6.5")

    // For `compose`. Creates a `ChartStyle` based on an M3 Material Theme.
    implementation("com.patrykandpatrick.vico:compose-m3:1.6.5")

    implementation("com.google.android.exoplayer:exoplayer:2.18.7")

    implementation(platform("com.google.firebase:firebase-bom:32.1.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
}