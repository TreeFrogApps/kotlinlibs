package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.flow.*

fun <T> Flow<T>.doOnSubscribe(action: () -> Unit): Flow<T> = flow {
    action()
    collect { emit(it) }
}

fun <T> Flow<T>.doOnComplete(action: () -> Unit): Flow<T> = flow {
    emitAll(this@doOnComplete)
    action()
}

fun <T> Flow<T>.doFinally(action: () -> Unit): Flow<T> = flow {
    try {
        emitAll(this@doFinally)
    } finally {
        action()
    }
}

fun <T> Flow<T>.doOnEach(action: (t: T) -> Unit): Flow<T> = flow {
    onEach {  }
    collect { action(it); emit(it) }
}

fun <T> Flow<T>.doAfterEach(action: (t: T) -> Unit): Flow<T> = flow {
    collect { emit(it); action(it) }
}