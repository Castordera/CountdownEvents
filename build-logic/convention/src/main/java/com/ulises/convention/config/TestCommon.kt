package com.ulises.convention.config

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureAndroidTest(
    commonExtension: CommonExtension<*, *, *, *, *>
) {
    commonExtension.apply {
        dependencies {
            add("androidTestImplementation", libs.get("androidx-test-ext-junit"))
            add("androidTestImplementation", libs.get("espresso-core"))
            add("androidTestImplementation", libs.get("androidx-ui-test-junit4"))
            add("debugImplementation", libs.get("androidx-ui-test-manifest"))
        }
    }
}

fun Project.configureUnitTest(
    commonExtension: CommonExtension<*, *, *, *, *>
) {
    commonExtension.apply {
        dependencies {
            add("testImplementation", libs.get("junit"))
            add("testImplementation", libs.get("mockk"))
        }
    }
}