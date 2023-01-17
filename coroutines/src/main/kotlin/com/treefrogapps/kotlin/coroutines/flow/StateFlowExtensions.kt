package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed


fun <T> Flow<T>.asStateFlow(
    scope: CoroutineScope,
    started: SharingStarted = WhileSubscribed(stopTimeoutMillis = 5_000),
    initialValue: T
): StateFlow<T> =
    stateIn(
        scope = scope,
        started = started,
        initialValue = initialValue)

fun <T> Flow<T>.stateInWhileSubscribed(
    scope: CoroutineScope,
    stopTimeoutMillis : Long = 5_000,
    initialValue: T
): StateFlow<T> =
    stateIn(
        scope = scope,
        started = WhileSubscribed(stopTimeoutMillis = stopTimeoutMillis),
        initialValue = initialValue)

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

fun <T> nullableMutableStateFlowOf(t: T? = null): MutableStateFlow<T?> = MutableStateFlow(t)

fun <T> mutableStateFlowOf(t: T): MutableStateFlow<T> = MutableStateFlow(t)