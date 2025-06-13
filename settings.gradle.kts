pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CatApi"
include(":app")
include(":core:navigation")
include(":core:model")
include(":core:network")
include(":core:designsystem")
include(":core:data")
include(":core:utils")
include(":feature:home")
include(":feature:details")
include(":feature:common")
