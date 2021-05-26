import org.jetbrains.kotlin.config.KotlinCompilerVersion

group = "com.treefrogapps.kotlin.coroutines"
project.extra["name"] = "coroutines"
version = "1.5.0"

dependencies {
    // Kotlin
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")

    testImplementation("junit:junit:4.13.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")
}