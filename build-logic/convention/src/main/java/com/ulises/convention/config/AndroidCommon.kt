package com.ulises.convention.config

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCommon(
    extension: CommonExtension<*, *, *, *, *, *>
) {
    extension.apply {
        compileSdk = 34

        defaultConfig {
            minSdk = 28
            vectorDrawables {
                useSupportLibrary = true
            }
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        packaging.resources {
            excludes += "/META-INF/*"
        }

        dependencies {
            add("implementation", libs.library("timber"))
        }
    }
}

internal fun Project.configureAndroidAppCommon(
    extension: ApplicationExtension
) {
    extension.apply {
        defaultConfig {
            targetSdk = 34
        }
    }
}

internal fun Project.configureCommonDependencies() {
    dependencies {
        add("implementation", libs.library("android-core-ktx"))
    }
}