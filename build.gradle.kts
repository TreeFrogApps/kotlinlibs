import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21" apply false
    `maven-publish`
    `java-library`
}

allprojects {

    repositories {
        mavenCentral()
        mavenLocal()
    }
}

subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri(project.findProperty("gpr_url_kotlin_libs") as String)
            credentials {
                username = project.findProperty("gpr_user") as String
                password = project.findProperty("gpr_public_key") as String
            }
        }
    }

    java {
        withJavadocJar()
        withSourcesJar()
    }

    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri(project.findProperty("gpr_url_kotlin_libs") as String)
                credentials {
                    username = project.findProperty("gpr_user") as String
                    password = project.findProperty("gpr_key") as String
                }
            }
        }

        publications {
            register("gprRelease", MavenPublication::class) {
                from(components["java"])
            }
        }
    }

    val javaVersion = "1.8"

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = javaVersion
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    tasks.withType<Test> {
        testLogging { events = mutableSetOf(PASSED, SKIPPED, FAILED) }
    }
}