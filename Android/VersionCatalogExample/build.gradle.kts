// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.plugin) apply false
    alias(libs.plugins.parcelize.plugin) apply false
    alias(libs.plugins.hilt.plugin) apply false
    alias(libs.plugins.ksp.plugin) apply false
    alias(libs.plugins.kapt.plugin) apply false
}

extra.apply {

}