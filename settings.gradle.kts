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
includeBuild("build-logic")

rootProject.name = "CountdownApp"
gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))
include(":app")
include(":domain")
include(":usecase")
include(":data")
include(":design:components")
include(":design:theme")
include(":feature:list")
include(":common:preview-data")
include(":feature:addEvent")
include(":feature:event-detail")
include(":common:date-utils")
include(":common:navigation")
include(":common:datastore")
include(":common:resources")
include(":common:database")
include(":common:notification")
