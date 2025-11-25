pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        // Alternate Maven mirrors to avoid DNS/resolution issues with repo.maven.apache.org
        maven {
            url = uri("https://cache-redirector.jetbrains.com/maven-central")
        }
        maven {
            url = uri("https://repo1.maven.org/maven2/")
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        // Alternate Maven mirrors for dependencies
        maven {
            url = uri("https://cache-redirector.jetbrains.com/maven-central")
        }
        maven {
            url = uri("https://repo1.maven.org/maven2/")
        }
        mavenCentral()
    }
}

rootProject.name = "BDF"
include(":app")
 