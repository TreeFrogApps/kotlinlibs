import org.jetbrains.kotlin.config.KotlinCompilerVersion

group = "com.treefrogapps.kotlin.coroutines"
project.extra["name"] = "coroutines"
version = "1.0.1"

dependencies {
    // Kotlin
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")

    testImplementation("junit:junit:4.12")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.9")
}