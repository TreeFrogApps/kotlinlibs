group = "com.treefrogapps.kotlin.coroutines"
project.extra["name"] = "coroutines"
version = "2.5.6"

dependencies {
    // Kotlin
    implementation(libs.kotlin.stdlib)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
}