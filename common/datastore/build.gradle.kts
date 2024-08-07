plugins {
    id("countdown.android.library.compose")
    id("countdown.android.kotlin")
    id("countdown.android.common")
    id("countdown.android.test")
    id("countdown.test")
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