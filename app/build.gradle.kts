plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = Versions.App.TARGET_SDK

    defaultConfig {
        applicationId = "com.github.hiking93.m3demo"
        targetSdk = Versions.App.TARGET_SDK
        minSdk = Versions.App.MIN_SDK
        versionCode = Versions.App.VERSION_CODE
        versionName = Versions.App.VERSION_NAME
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.Kotlin.KOTLIN}")
    implementation("androidx.appcompat:appcompat:${Versions.AndroidX.APP_COMPAT}")
    implementation("androidx.core:core-ktx:${Versions.AndroidX.CORE}")
    implementation("com.google.android.flexbox:flexbox:${Versions.Google.FLEXBOX}")
    implementation("com.google.android.material:material:${Versions.Google.MATERIAL}")
}
