package com.treefrogapps.kotlin.coroutines.flow.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.take

fun <T> Flow<T>.first(): Flow<T> = take(1)

fun <T> Flow<T>.firstIf(block: (T) -> Boolean): Flow<T> = take(1).filter { block(it) }

fun <T> Flow<T>.firstOrDefault(default: T, block: (T) -> Boolean): Flow<T> =
    take(1)
        .filter { block(it) }
        .onEmpty { emit(default) }