package com.ulises.convention.config

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildFeatures
import com.android.build.api.dsl.ComposeOptions
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    applicationExtension: ApplicationExtension,
) {
    applicationExtension.apply {
        buildFeatures.configure()
        composeOptions.configure()
        configureComposeDependencies()
    }
}

internal fun Project.configureAndroidCompose(
    libraryExtension: LibraryExtension,
) {
    libraryExtension.apply {
        buildFeatures.configure()
        composeOptions.configure()
        configureComposeDependencies()
    }
}

private fun BuildFeatures.configure() {
    this.apply {
        compose = true
    }
}

private fun ComposeOptions.configure() {
    this.apply {

    }
}

private fun Project.configureComposeDependencies() {
    dependencies {
        val boom = libs.library("androidx-compose-bom")
        implementation(platform(boom))
        implementation(libs.library("androidx-ui"))
        implementation(libs.library("androidx-ui-tooling-preview"))
        debugImplementation(libs.library("androidx-ui-tooling"))
        implementation(libs.library("compose-material3"))
        implementation(libs.library("compose-icons"))
    }
}