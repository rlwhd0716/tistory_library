plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.plugin)
    alias(libs.plugins.parcelize.plugin)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.ksp.plugin)
    alias(libs.plugins.kapt.plugin)
}

android {
    namespace = "com.example.webviewexam"
    compileSdk = rootProject.extra["currentSdk"] as Int

    defaultConfig {
        applicationId = "com.example.webviewexam"
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["currentSdk"] as Int
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isDebuggable = true

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        release {
            isDebuggable = false

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

    flavorDimensions("server")
    productFlavors {
        create("devs") {
            dimension = "server"
            buildConfigField("Boolean", "DEBUG", "true")
            buildConfigField("String", "API_URL", "\"http://192.168.0.1:8080/api/v1/\"")
            buildConfigField("String", "WEBVIEW_URL", "\"http://192.168.0.1:8080\"")
        }
        create("real") {
            dimension = "server"
            buildConfigField("Boolean", "DEBUG", "false")
            buildConfigField("String", "API_URL", "\"http://test/api/v1\"")
            buildConfigField("String", "WEBVIEW_URL", "\"http://test/\"")
        }
    }
}

dependencies {

    // Clean Architecture
    implementation(project(mapOf("path" to ":data")))
    implementation(project(mapOf("path" to ":domain")))

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

    implementation(libs.android.timber)

    //viewModel, livedata
//    implementation(libs.bundles.lifecycle)
//    ksp(libs.lifecycle.compiler)

    // Retrofit, Okhttp, gson
    implementation(libs.bundles.retrofit)

    // Coroutine
    implementation(libs.bundles.coroutines)
}

kapt {
    correctErrorTypes = true
}