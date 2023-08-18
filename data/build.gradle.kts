plugins {
    id("countdown.kotlin.library")
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.javax.inject)
    implementation(libs.coroutines.core)
}