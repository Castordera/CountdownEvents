package com.ulises.convention.config

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureAndroidTest(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        dependencies {
            androidTestImplementation(libs.library("androidx-test-ext-junit"))
            androidTestImplementation(libs.library("espresso-core"))
            androidTestImplementation(libs.library("androidx-ui-test-junit4"))
            androidTestImplementation(libs.library("androidx-ui-test-manifest"))
        }
    }
}

fun Project.configureUnitTest(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        dependencies {
            testImplementation(libs.library("junit"))
            testImplementation(libs.library("mockk"))
        }
    }
}