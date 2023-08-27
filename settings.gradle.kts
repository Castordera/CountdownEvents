pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("build-logic")
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "CountdownApp"
include(":app")
include(":domain")
include(":usecase")
include(":data")
include(":design:components")
include(":design:theme")
include(":feature:list")
include(":common:preview-data")
