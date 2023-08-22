@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.plugin)
    alias(libs.plugins.parcelize.plugin)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.ksp.plugin)
    alias(libs.plugins.kapt.plugin)
}

android {
    namespace = "com.example.versioncatalogexample"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.versioncatalogexample"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // project default
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.android.test.junit)
    androidTestImplementation(libs.android.test.core)

    // constraint
    implementation(libs.androidx.constraintlayout)

    //hilt
    implementation(libs.bundles.hilt)
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
}

kapt {
    correctErrorTypes = false
}