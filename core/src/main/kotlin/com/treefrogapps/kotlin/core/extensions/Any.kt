package com.treefrogapps.kotlin.core.extensions

inline fun <T> T?.ifNull(func: () -> Unit) {
    this ?: func()
}

inline fun <T> T?.orElse(func: () -> T & Any): T & Any =
    this ?: func()

inline fun <T : Any?> T?.orElseMaybe(func: () -> T?): T? =
    this ?: func()

fun <T> T?.orElse(default: T): T =
    this ?: default