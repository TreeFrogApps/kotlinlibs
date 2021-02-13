package com.treefrogapps.kotlin.coroutines.flow.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.take

fun <T> Flow<T>.first(): Flow<T> = take(1)

inline fun <T> Flow<T>.firstIf(crossinline block: suspend (T) -> Boolean): Flow<T> = take(1).filter(block)

inline fun <T> Flow<T>.firstOrDefault(default: T, crossinline block: suspend (T) -> Boolean): Flow<T> =
    take(1)
        .filter(block)
        .onEmpty { emit(default) }