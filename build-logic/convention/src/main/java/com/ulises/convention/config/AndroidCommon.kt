package com.ulises.convention.config

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

internal fun Project.configureAndroidCommon(
    extension: CommonExtension<*, *, *, *, *>
) {
    extension.apply {
        compileSdk = 34

        defaultConfig {
            minSdk = 30
            vectorDrawables {
                useSupportLibrary = true
            }
        }

        packaging.resources {
            excludes += "'/META-INF/{AL2.0,LGPL2.1}'"
        }
    }
}