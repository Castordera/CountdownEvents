package com.ulises.convention.config

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {

        }

        dependencies {
            val boom = libs.library("androidx-compose-bom")
            implementation(platform(boom))
            implementation(libs.library("androidx-ui"))
            implementation(libs.library("androidx-ui-tooling-preview"))
//            implementation(libs.library("androidx-ui-animation"))
            debugImplementation(libs.library("androidx-ui-tooling"))
            implementation(libs.library("compose-material3"))
        }
    }
}