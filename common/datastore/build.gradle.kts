plugins {
    alias(libs.plugins.castorena.bl.lib.compose)
    alias(libs.plugins.castorena.bl.kotlin)
    alias(libs.plugins.castorena.bl.common)
    alias(libs.plugins.castorena.bl.test.android)
    alias(libs.plugins.castorena.bl.test.unit)
}

android {
    namespace = "com.ulises.datastore"
}

dependencies {
    implementation(libs.data.store)
    implementation(libs.javax.inject)
    //
    implementation(project(":data"))
}