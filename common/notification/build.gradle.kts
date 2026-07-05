plugins {
    alias(libs.plugins.castorena.bl.compose.lib)
}

android {
    namespace = "com.ulises.common.notification"
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
}