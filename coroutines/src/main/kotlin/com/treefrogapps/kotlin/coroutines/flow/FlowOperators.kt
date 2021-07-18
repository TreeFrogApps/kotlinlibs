package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
fun <T> Flow<T>.subscribe(scope: CoroutineScope,
                          next: suspend (t: T) -> Unit,
                          error: (Throwable) -> Unit = {},
                          complete: suspend () -> Unit = {}): Job =
        onEach(next::invoke)
                .onCompletion { if (it == null) complete() }
                .catch { error(it) }
                .launchIn(scope)

@ExperimentalCoroutinesApi
fun <T> Flow<T>.subscribe(scope: CoroutineScope, observer: FlowObserver<T>): Job =
        onEach(observer::onNext)
                .onCompletion { if (it == null) observer.onComplete() }
                .catch { observer.onError(it) }
                .launchIn(scope)

@ExperimentalCoroutinesApi
internal fun <T> Flow<T>.subscribe(emitterScope: FlowEmitterScope<T>): Job =
        onEach(emitterScope::onNext)
                .onCompletion { if (it == null) emitterScope.onComplete() }
                .catch { emitterScope.onError(it) }
                .launchIn(emitterScope)

fun <T> Flow<T>.launchCompletable(
        scope: CoroutineScope,
        onCompleted: suspend () -> Unit,
        onError: suspend (Throwable) -> Unit = {}): Job =
        onCompletion { if (it == null) onCompleted() }
                .catch { onError(it) }
                .launchIn(scope)

fun <T> Flow<T>.launchSingle(
        scope: CoroutineScope,
        onSuccess: suspend (T) -> Unit,
        onError: suspend (Throwable) -> Unit = {}): Job =
        take(1)
                .onEach { onSuccess(it) }
                .catch { onError(it) }
                .launchIn(scope)

fun <T> Flow<T>.launchMaybe(
        scope: CoroutineScope,
        onSuccess: suspend (T) -> Unit,
        onEmpty: suspend () -> Unit,
        onError: suspend (Throwable) -> Unit = {}): Job =
        take(1)
                .onEmpty { onEmpty() }
                .onEach { onSuccess(it) }
                .catch { onError(it) }
                .launchIn(scope)