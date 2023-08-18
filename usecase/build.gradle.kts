plugins {
    id("countdown.kotlin.library")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.javax.inject)
    implementation(libs.coroutines.core)
}