package com.treefrogapps.kotlin.core.extensions


inline fun <T> Result<T>.onComplete(action : () -> Unit): Result<T> {
    action()
    return this
}

fun <T> Result<T>.getOrDefault(value: T): T = getOrNull() ?: value

fun et() {
    Result.success(1).onSuccess {  }
}
