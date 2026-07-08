plugins {
    alias(libs.plugins.castorena.bl.compose.lib)
    alias(libs.plugins.castorena.bl.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ulises.common.database"
}

dependencies {
    //
    implementation(project(":domain"))
    implementation(project(":data"))
    //  Room
    implementation(libs.bundles.room.core)
    ksp(libs.room.coompiler)
    //  Others
    implementation(libs.javax.inject)
}