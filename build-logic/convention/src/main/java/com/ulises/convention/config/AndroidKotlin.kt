package com.ulises.convention.config

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CompileOptions
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

internal fun Project.configureAndroidKotlin(
    applicationExtension: ApplicationExtension,
) {
    applicationExtension.apply {
        compileOptions.configure()
    }
}

internal fun Project.configureAndroidKotlin(
    libraryExtension: LibraryExtension,
) {
    libraryExtension.apply {
        compileOptions.configure()
    }
}

private fun CompileOptions.configure() {
    this.apply {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}