package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

fun <T> Flow<T>.launch(
        scope: CoroutineScope,
        onEach: suspend (t: T) -> Unit = {},
        onCompleted: suspend () -> Unit = {},
        onError: suspend (Throwable) -> Unit = {}): Job =
        onEach(onEach::invoke)
                .onCompletion { if (it == null) onCompleted() }
                .catch { onError(it) }
                .launchIn(scope)

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
        first()
                .onEach { onSuccess(it) }
                .catch { onError(it) }
                .launchIn(scope)

fun <T> Flow<T>.launchMaybe(
        scope: CoroutineScope,
        onSuccess: suspend (T) -> Unit,
        onEmpty: suspend () -> Unit,
        onError: suspend (Throwable) -> Unit = {}): Job =
        first()
                .onEmpty { onEmpty() }
                .onEach { onSuccess(it) }
                .catch { onError(it) }
                .launchIn(scope)