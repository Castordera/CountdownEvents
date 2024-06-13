plugins {
    id("countdown.kotlin.library")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.javax.inject)
    implementation(libs.coroutines.core)
    //
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
}

tasks.withType<Test> {
    useJUnitPlatform()
}