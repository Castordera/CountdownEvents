plugins {
    id("countdown.android.library.compose")
    id("countdown.android.kotlin")
    id("countdown.android.common")
    id("countdown.android.test")
    id("countdown.test")
}

android {
    namespace = "com.ulises.components"
}

dependencies {
    //  Projects
    implementation(project(":design:theme"))
    implementation(project(":common:resources"))
}