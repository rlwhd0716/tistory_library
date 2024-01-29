plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.plugin)
    alias(libs.plugins.parcelize.plugin)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.ksp.plugin)
    alias(libs.plugins.kapt.plugin)
}

android {
    namespace = "com.example.cleanarchitecturehilt"
    compileSdk = rootProject.extra["currentSdk"] as Int

    defaultConfig {
        applicationId = "com.example.cleanarchitecturehilt"
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["currentSdk"] as Int
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = rootProject.extra["JDK-Version"] as JavaVersion
        targetCompatibility = rootProject.extra["JDK-Version"] as JavaVersion
    }

    kotlinOptions {
        jvmTarget = rootProject.extra["JDK-Target"] as String
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    dataBinding {
        enable = true

    }

    lint.checkReleaseBuilds = false
    lint.abortOnError = false
}

dependencies {

    // project default
    implementation(libs.bundles.ui)
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.android.test.junit)
    androidTestImplementation(libs.android.test.core)

    // constraint
    implementation(libs.bundles.component)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    //viewModel, livedata
    implementation(libs.bundles.lifecycle)
    ksp(libs.lifecycle.compiler)

    // Retrofit, Okhttp, gson
    implementation(libs.bundles.retrofit)

    // Coroutine
    implementation(libs.bundles.coroutines)

    // Image Caching
    implementation(libs.glide.core)
    ksp(libs.glide.compiler)

    implementation(libs.git.mpchart)

    // room
    implementation(libs.bundles.room)
    testImplementation(libs.room.testing)
    ksp(libs.room.compiler)

    // Photo View
    implementation(libs.git.photoview)
}

kapt {
    correctErrorTypes = true
}