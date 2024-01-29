// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.plugin) apply false
    alias(libs.plugins.parcelize.plugin) apply false
    alias(libs.plugins.kapt.plugin) apply false
    alias(libs.plugins.hilt.plugin) apply false
    alias(libs.plugins.ksp.plugin) apply false
}


ext.apply {
    set("currentSdk", 34)
    set("minSdk", 24)

    set("JDK-Version", JavaVersion.VERSION_17)
    set("JDK-Target", "17")
}