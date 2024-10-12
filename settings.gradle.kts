rootProject.name = "kotlinlibs"
include(
    "core",
    "coroutines"
)

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven {
            name = "GitHubPackages"
            url = uri(locate(key = "gpr_url_kotlin_libs"))
            credentials {
                username = locate(key = "gpr_user")
                password = locate(key = "gpr_public_key")
            }
        }
    }
}

fun ExtensionAware.locate(key: String): String =
    extra.get(key) as? String
        ?: System.getenv(key)
        ?: ""
