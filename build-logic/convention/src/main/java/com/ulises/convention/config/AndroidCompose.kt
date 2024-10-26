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
            add("implementation", libs.library("compose-ui"))
            add("implementation", libs.library("compose-preview"))
            add("implementation", libs.library("compose-animation"))
            add("debugImplementation", libs.library("compose-tooling"))
            // Material as well
            add("implementation", libs.library("compose-material3"))
        }
    }
}