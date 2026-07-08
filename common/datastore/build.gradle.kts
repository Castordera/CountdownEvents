plugins {
    alias(libs.plugins.castorena.bl.compose.lib)
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