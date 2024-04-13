package com.ulises.convention.config

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureAndroidTest(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        dependencies {
            add("androidTestImplementation", libs.library("androidx-test-ext-junit"))
            add("androidTestImplementation", libs.library("espresso-core"))
            add("androidTestImplementation", libs.library("androidx-ui-test-junit4"))
            add("debugImplementation", libs.library("androidx-ui-test-manifest"))
        }
    }
}

fun Project.configureUnitTest(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        dependencies {
            add("testImplementation", libs.library("junit"))
            add("testImplementation", libs.library("mockk"))
        }
    }
}