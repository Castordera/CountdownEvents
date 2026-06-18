package com.ulises.convention.config

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.DefaultConfig
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.Packaging
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCommon(
    extension: ApplicationExtension,
) {
    extension.apply {
        compileSdk = 36
        defaultConfig.configure()
        packaging.configure()
        configureDependencies()
    }
}

internal fun Project.configureAndroidCommon(
    extension: LibraryExtension,
) {
    extension.apply {
        compileSdk = 36
        defaultConfig.configure()
        packaging.configure()
        configureDependencies()
    }
}

private fun Project.configureDependencies() {
    dependencies {
        implementation(libs.library("timber"))
        implementation(libs.library("android-core-ktx"))
    }
}

private fun DefaultConfig.configure() {
    this.apply {
        minSdk = 28
        vectorDrawables {
            useSupportLibrary = true
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

private fun Packaging.configure() {
    this.apply {
        resources {
            excludes += "'/META-INF/{AL2.0,LGPL2.1}'"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}