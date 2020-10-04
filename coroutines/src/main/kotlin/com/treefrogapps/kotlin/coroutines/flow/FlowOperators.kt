package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
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
    collect { action(it); emit(it) }
}

fun <T> Flow<T>.doAfterEach(action: (t: T) -> Unit): Flow<T> = flow {
    collect { emit(it); action(it) }
}

@ExperimentalCoroutinesApi
fun <T> Flow<T>.subscribe(scope: CoroutineScope,
                          each: suspend (t: T) -> Unit,
                          error: (Throwable) -> Unit = {},
                          complete: suspend () -> Unit = {}): Job =
        onEach(each::invoke)
            .onCompletion { complete() }
            .catch { error(it) }
            .launchIn(scope)

@ExperimentalCoroutinesApi
fun <T> Flow<T>.subscribe(scope: CoroutineScope, observer: FlowObserver<T>): Job =
        onEach(observer::onNext)
            .onCompletion { observer.onComplete() }
            .catch { observer.onError(it) }
            .launchIn(scope)

@ExperimentalCoroutinesApi
fun <T> Flow<T>.subscribe(emitterScope: FlowEmitterScope<T>): Job =
        onEach(emitterScope::onNext)
            .onCompletion { emitterScope.onComplete() }
            .catch { emitterScope.onError(it) }
            .launchIn(emitterScope)