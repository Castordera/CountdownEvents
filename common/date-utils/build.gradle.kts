plugins {
    id("countdown.kotlin.library")
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.javax.inject)
}