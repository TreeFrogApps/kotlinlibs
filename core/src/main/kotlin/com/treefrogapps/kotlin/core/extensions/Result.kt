package com.treefrogapps.kotlin.core.extensions

inline fun <T> Result<T>.onComplete(action: () -> Unit): Result<T> {
    action()
    return this
}

fun <T> Result<T>.getOrDefault(value: T): T = getOrNull() ?: value

inline fun <R, E : Throwable> runCatchable(
    catchable: List<Class<in E>> = listOf(),
    block: () -> R
): Result<R> =
    try {
        Result.success(block())
    } catch (e: Throwable) {
        if (catchable.any { e::class.java == it }) {
            Result.failure(e)
        } else throw e
    }

inline fun <R> runCatchable(
    vararg catchable: Class<out Throwable>,
    block: () -> R
): Result<R> =
    try {
        Result.success(block())
    } catch (e: Throwable) {
        if (catchable.any { e::class.java == it }) {
            Result.failure(e)
        } else throw e
    }


