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

/**
 * Returns a [Flow] whose values are generated with [transform] function by combining
 * the most recently emitted values by each flow.
 */
public fun <T1, T2, T3, T4, T5, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    transform: suspend (T1, T2, T3, T4, T5) -> R
): Flow<R> = kotlinx.coroutines.flow.combine(
    kotlinx.coroutines.flow.combine(flow, flow2, flow3, ::Triple) ,
    kotlinx.coroutines.flow.combine(flow4, flow5, ::Pair)
) { t1, t2 ->
    transform(
        t1.first,
        t1.second,
        t1.third,
        t2.first,
        t2.second)
}

public fun <T1, T2, T3, T4, T5, T6, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    transform: suspend (T1, T2, T3, T4, T5, T6) -> R
): Flow<R> = kotlinx.coroutines.flow.combine(
    kotlinx.coroutines.flow.combine(flow, flow2, flow3, ::Triple) ,
    kotlinx.coroutines.flow.combine(flow4, flow5, flow6, ::Triple)
) { t1, t2 ->
    transform(
        t1.first,
        t1.second,
        t1.third,
        t2.first,
        t2.second,
        t2.third)
}

public fun <T1, T2, T3, T4, T5, T6, T7, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7) -> R
): Flow<R> = kotlinx.coroutines.flow.combine(
    kotlinx.coroutines.flow.combine(flow, flow2, flow3, ::Triple),
    kotlinx.coroutines.flow.combine(flow4, flow5, flow6, ::Triple),
    flow7,
) { t1, t2, t3 ->
    transform(
        t1.first,
        t1.second,
        t1.third,
        t2.first,
        t2.second,
        t2.third,
        t3)
}

public fun <T1, T2, T3, T4, T5, T6, T7, T8, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8) -> R
): Flow<R> = kotlinx.coroutines.flow.combine(
    kotlinx.coroutines.flow.combine(flow, flow2, flow3, ::Triple),
    kotlinx.coroutines.flow.combine(flow4, flow5, flow6, ::Triple),
    kotlinx.coroutines.flow.combine(flow7, flow8, ::Pair),
) { t1, t2, t3 ->
    transform(
        t1.first,
        t1.second,
        t1.third,
        t2.first,
        t2.second,
        t2.third,
        t3.first,
        t3.second)
}

public fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> combine(
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
): Flow<R> = kotlinx.coroutines.flow.combine(
    kotlinx.coroutines.flow.combine(flow, flow2, flow3, ::Triple),
    kotlinx.coroutines.flow.combine(flow4, flow5, flow6, ::Triple),
    kotlinx.coroutines.flow.combine(flow7, flow8, flow9, ::Triple),
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
        t3.third)
}