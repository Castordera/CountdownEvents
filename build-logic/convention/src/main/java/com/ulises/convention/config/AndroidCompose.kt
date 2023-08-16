package com.ulises.convention.config

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = "1.4.3"// Todo(Make dynamic)
        }

        dependencies {
            add("implementation", libs.findLibrary("compose.ui").get())
            add("implementation", libs.findLibrary("compose-preview").get())
        }
    }
}