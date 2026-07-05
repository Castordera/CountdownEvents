plugins {
    alias(libs.plugins.castorena.bl.lib.compose)
    alias(libs.plugins.castorena.bl.kotlin)
    alias(libs.plugins.castorena.bl.common)
}

android {
    namespace = "com.ulises.common.notification"
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
}