import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30" apply false
    `maven-publish`
    `java-library`
}

allprojects {

    repositories {
        mavenCentral()
        mavenLocal()
        jcenter()
    }
}

subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
        jcenter()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/treefrogapps/kotlinlibs")
            credentials {
                username = project.findProperty("gpr.user") as String
                password = project.findProperty("gpr.public_key") as String
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
                url = uri("https://maven.pkg.github.com/treefrogapps/kotlinlibs")
                credentials {
                    username = project.findProperty("gpr.user") as String
                    password = project.findProperty("gpr.key") as String
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