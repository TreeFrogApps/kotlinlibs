package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.launch


fun <T> Flow<T>.asSharedFlow(scope: CoroutineScope, started: SharingStarted = Lazily, replay: Int = 1): SharedFlow<T> =
    this.shareIn(scope, started, replay)

fun <T> MutableSharedFlow<T>.send(scope: CoroutineScope, value: T) {
    scope.launch { emit(value) }
}

suspend fun <T> MutableSharedFlow<T>.emitAll(vararg value : T) {
    value.forEach { v -> emit(v) }
}