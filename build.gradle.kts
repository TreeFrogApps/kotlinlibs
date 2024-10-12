import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    `maven-publish`
    `java-library`
}

subprojects {

    apply(plugin = rootProject.libs.plugins.kotlin.jvm.get().pluginId)
    apply<MavenPublishPlugin>()

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
            register(name = "gprRelease", type = MavenPublication::class) {
                from(components["java"])
            }
        }
    }

    tasks {
        withType<KotlinCompile> {
            compilerOptions { jvmTarget.set(JvmTarget.JVM_1_8) }
        }
        withType<JavaCompile> {
            sourceCompatibility = JavaVersion.VERSION_1_8.toString()
            targetCompatibility = JavaVersion.VERSION_1_8.toString()
        }
        withType<Test> {
            testLogging { events = mutableSetOf(PASSED, SKIPPED, FAILED) }
        }
    }
}