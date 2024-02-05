plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.plugin)
    alias(libs.plugins.parcelize.plugin)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.kapt.plugin)
}

android {
    namespace = "com.example.webviewexam.data"
    compileSdk = rootProject.extra["currentSdk"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minSdk"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    // Clean Architecture
    implementation(project(mapOf("path" to ":domain")))

    // project default
    implementation(libs.bundles.ui)
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.android.test.junit)
    androidTestImplementation(libs.android.test.core)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Retrofit, Okhttp, gson
    implementation(libs.bundles.retrofit)
}