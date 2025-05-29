package com.ulises.convention.config

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCommon(
    extension: CommonExtension<*, *, *, *, *, *>
) {
    extension.apply {
        compileSdk = 35

        defaultConfig {
            minSdk = 28
            vectorDrawables {
                useSupportLibrary = true
            }
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        packaging.resources {
            excludes += "'/META-INF/{AL2.0,LGPL2.1}'"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }

        dependencies {
            implementation(libs.library("timber"))
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
        implementation(libs.library("android-core-ktx"))
    }
}