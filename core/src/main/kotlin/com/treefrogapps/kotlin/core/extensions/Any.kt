package com.treefrogapps.kotlin.core.extensions

inline fun <T> T?.ifNull(func: () -> Unit) {
    if (this == null) func()
}

inline fun <T> T?.orElse(func: () -> T & Any): T =
    when {
        this == null -> func()
        else -> this
    }

inline fun <T> T?.orElseMaybe(func: () -> T?): T? =
    when {
        this == null -> func()
        else -> this
    }

fun <T> T?.orElse(default: T): T =
    when {
        this == null -> default
        else -> this
    }