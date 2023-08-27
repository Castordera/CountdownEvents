import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.ulises.convention.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "countdown.android.app.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "countdown.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidKotlin") {
            id = "countdown.android.kotlin"
            implementationClass = "AndroidKotlinConventionPlugin"
        }
        register("androidCommon") {
            id = "countdown.android.common"
            implementationClass = "AndroidCommonConventionPlugin"
        }
        register("kotlinLibraryCommon") {
            id = "countdown.kotlin.library"
            implementationClass = "KotlinLibraryCommonConventionPlugin"
        }
        register("androidTest") {
            id = "countdown.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
    }
}