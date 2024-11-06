group = "com.treefrogapps.kotlin.coroutines"
project.extra["name"] = "coroutines"
version = "2.5.4"

dependencies {
    // Kotlin
    implementation(libs.kotlin.stdlib)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}