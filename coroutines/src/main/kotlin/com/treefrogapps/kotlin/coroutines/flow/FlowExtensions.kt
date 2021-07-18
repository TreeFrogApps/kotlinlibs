package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.flow.*

fun <T> Flow<T>.first(): Flow<T> = take(1)

inline fun <T> Flow<T>.firstIf(crossinline block: suspend (T) -> Boolean): Flow<T> = take(1).filter(block)

inline fun <T> Flow<T>.firstOrDefault(default: T, crossinline block: suspend (T) -> Boolean): Flow<T> =
    take(1)
        .filter(block)
        .onEmpty { emit(default) }

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

fun <T> Flow<T>.andThen(other: Flow<T>): Flow<T> = onCompletion { if(it == null) emitAll(other) }

/**
 * Chain flows together that will run sequentially in the order provided
 *
 * @param links the array of [Flow] to concat
 * @return the first [Flow] element with
 */
fun <T> chainFlow(vararg links: Flow<T>): Flow<T> = flow { links.forEach { emitAll(it) } }

fun <T> Flow<T>.doOnEach(action: (t: T) -> Unit): Flow<T> = flow {
    collect { action(it); emit(it) }
}

fun <T> Flow<T>.doAfterEach(action: (t: T) -> Unit): Flow<T> = flow {
    collect { emit(it); action(it) }
}

/**
 * Returns a [Flow] whose values are generated with [transform] function by combining
 * the most recently emitted values by each flow.
 */
fun <T1, T2, T3, T4, T5, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    transform: suspend (T1, T2, T3, T4, T5) -> R
): Flow<R> = combine(
    combine(flow, flow2, flow3, ::Triple),
    combine(flow4, flow5, ::Pair)
) { t1, t2 ->
    transform(
        t1.first,
        t1.second,
        t1.third,
        t2.first,
        t2.second
    )
}

fun <T1, T2, T3, T4, T5, T6, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    transform: suspend (T1, T2, T3, T4, T5, T6) -> R
): Flow<R> = combine(
    combine(flow, flow2, flow3, ::Triple),
    combine(flow4, flow5, flow6, ::Triple)
) { t1, t2 ->
    transform(
        t1.first,
        t1.second,
        t1.third,
        t2.first,
        t2.second,
        t2.third
    )
}

fun <T1, T2, T3, T4, T5, T6, T7, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7) -> R
): Flow<R> = combine(
    combine(flow, flow2, flow3, ::Triple),
    combine(flow4, flow5, flow6, ::Triple),
    flow7,
) { t1, t2, t3 ->
    transform(
        t1.first,
        t1.second,
        t1.third,
        t2.first,
        t2.second,
        t2.third,
        t3
    )
}

fun <T1, T2, T3, T4, T5, T6, T7, T8, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8) -> R
): Flow<R> = combine(
    combine(flow, flow2, flow3, ::Triple),
    combine(flow4, flow5, flow6, ::Triple),
    combine(flow7, flow8, ::Pair),
) { t1, t2, t3 ->
    transform(
        t1.first,
        t1.second,
        t1.third,
        t2.first,
        t2.second,
        t2.third,
        t3.first,
        t3.second
    )
}

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    flow9: Flow<T9>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R
): Flow<R> = combine(
    combine(flow, flow2, flow3, ::Triple),
    combine(flow4, flow5, flow6, ::Triple),
    combine(flow7, flow8, flow9, ::Triple),
) { t1, t2, t3 ->
    transform(
        t1.first,
        t1.second,
        t1.third,
        t2.first,
        t2.second,
        t2.third,
        t3.first,
        t3.second,
        t3.third
    )
}

inline fun <T1, reified T2, R> combine(
    flow: Flow<T1>,
    flow2: Iterable<Flow<T2>>,
    crossinline transform: suspend (T1, Array<T2>) -> R
): Flow<R> =
    combine(flow, combine(flow2) { it })
    { t1, t2 -> transform(t1, t2) }

inline fun <T1, T2, reified T3, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Iterable<Flow<T3>>,
    crossinline transform: suspend (T1, T2, Array<T3>) -> R
): Flow<R> =
    combine(flow, flow2, combine(flow3) { it })
    { t1, t2, t3 -> transform(t1, t2, t3) }
