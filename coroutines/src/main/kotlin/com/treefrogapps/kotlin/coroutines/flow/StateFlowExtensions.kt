package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed

fun <T> Flow<T>.asNullableStateFlow(
    scope: CoroutineScope,
    started: SharingStarted = Lazily,
    initialState: T? = null
): StateFlow<T?> =
    this.stateIn(scope, started, initialState)

fun <T> Flow<T>.asStateFlow(
    scope: CoroutineScope,
    started: SharingStarted = WhileSubscribed(stopTimeoutMillis = 5_000),
    initialState: T
): StateFlow<T> =
    this.stateIn(scope, started, initialState)

fun <T> MutableStateFlow<T>.set(t: T) {
    value = t
}

fun <T> MutableStateFlow<T>.get() = value

fun <T> MutableStateFlow<T>.update(block: (T) -> T) {
    value = block(value)
}

fun <T> MutableStateFlow<T?>.setToNull() {
    value = null
}

fun <T> nullableMutableStateFlow(t: T? = null): MutableStateFlow<T?> = MutableStateFlow(t)

fun <T> mutableStateFlowOf(t: T): MutableStateFlow<T> = MutableStateFlow(t)