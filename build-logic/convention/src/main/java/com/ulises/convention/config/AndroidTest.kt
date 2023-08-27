package com.ulises.convention.config

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureAndroidTest(
    commonExtension: CommonExtension<*, *, *, *, *>
) {
    commonExtension.apply {
        dependencies {
            add("androidTestImplementation", libs.findLibrary("androidx-test-ext-junit").get())
            add("androidTestImplementation", libs.findLibrary("espresso-core").get())
        }
    }
}