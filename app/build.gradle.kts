import com.bytecode.buildsrc.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("com.google.devtools.ksp")
}

android {
    namespace = Configs.Id
    compileSdk = Configs.CompileSdk

    defaultConfig {
        applicationId = "com.bytecode.petsy"
        minSdk = Configs.MinSdk
        targetSdk = Configs.TargetSdk
        versionCode = Configs.VersionCode
        versionName = Configs.VersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
    //Core
    implementation(SupportLib.CoreKtx)
    implementation(SupportLib.LifecycleRuntime)

    //Compose
    implementation(ComposeLib.Ui)
    implementation(ComposeLib.Preview)
    implementation(ComposeLib.Material)
    androidTestImplementation(AndroidTestingLib.ComposeTestJunit)
    debugImplementation(ComposeLib.Tooling)
    debugImplementation(ComposeLib.Manifest)
    implementation(ComposeLib.Activity)

    //Navigation
    implementation(NavigationLib.Navigation)
    implementation(AccompanistLib.Systemuicontroller)

    //Test
    testImplementation(TestingLib.Junit)
    androidTestImplementation(AndroidTestingLib.JunitExt)
    androidTestImplementation(AndroidTestingLib.EspressoCore)


}