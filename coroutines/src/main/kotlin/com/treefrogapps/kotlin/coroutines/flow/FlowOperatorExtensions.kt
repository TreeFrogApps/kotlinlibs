package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.flow.*
import kotlin.time.*

fun <T> Flow<T>.first(): Flow<T> = take(count = 1)

suspend fun <T> Flow<T>.firstValue(): T = first { true }

fun <T> Flow<T>.mapToUnit(): Flow<Unit> = map { }

fun <T> Flow<Collection<T>>.mapToFirst(): Flow<T> =
    filter(Collection<T>::isNotEmpty)
        .map(Collection<T>::first)

fun <T> Flow<Collection<T>>.mapToIndex(index: Int = 0): Flow<T> =
    map { it.elementAtOrNull(index) }
        .filterNotNull()

inline fun <T, R> Flow<Result<T>>.mapResult(
    crossinline onStart: suspend () -> R,
    crossinline onSuccess: suspend (T) -> R,
    crossinline onFailure: suspend (Throwable) -> R
): Flow<R> = mapResult(
    onSuccess = onSuccess,
    onFailure = onFailure
)
    .onStart { emit(onStart()) }

inline fun <T, R> Flow<Result<T>>.mapResult(
    crossinline onSuccess: suspend (T) -> R,
    crossinline onFailure: suspend (Throwable) -> R
): Flow<R> = map { result ->
    when {
        result.isSuccess -> onSuccess(result.getOrNull()!!)
        else -> onFailure(result.exceptionOrNull()!!)
    }
}

fun <T> Flow<T>.throttle(
    timeoutMillis: Long,
    timeSource: TimeSource = TimeSource.Monotonic
): Flow<T> =
    flow {
        var nextMarkedTime = timeSource.markNow()
        var hasEmitted = false
        suspend fun emitAndMarkNextTime(value: T) {
            emit(value)
            nextMarkedTime = timeSource.markNow().plus(duration = timeoutMillis.toDuration(unit = DurationUnit.MILLISECONDS))
        }
        collect { value ->
            if (hasEmitted) {
                if (nextMarkedTime.hasPassedNow()) {
                    emitAndMarkNextTime(value = value)
                }
            } else {
                hasEmitted = true
                emitAndMarkNextTime(value = value)
            }
        }
    }

inline fun <T> Flow<T>.firstIf(
    crossinline block: suspend (T) -> Boolean
): Flow<T> = filter(block).take(1)

inline fun <T> Flow<T>.firstIf(
    crossinline predicate: suspend (T) -> Boolean,
    noinline onFirst: suspend (T) -> Unit
): Flow<T> = firstIf(predicate)
    .onEach(onFirst)

inline fun <T> Flow<T>.firstOrDefault(default: T, crossinline block: suspend (T) -> Boolean): Flow<T> =
    take(1)
        .filter(block)
        .onEmpty { emit(default) }

fun <T> Flow<T>.dropFirst(): Flow<T> = drop(count = 1)

/**
 * On successful completion of current [Flow] then concat with other
 */
fun <T> Flow<T>.andThen(other: Flow<T>): Flow<T> = onCompletion { if (it == null) emitAll(other) }

/**
 * On start of current [Flow] emit a value
 */
fun <T> Flow<T>.startValue(value: T): Flow<T> = onStart { emit(value) }

/**
 * On start of current [Flow] emit a value
 */
inline fun <T> Flow<T>.startValue(crossinline block: () -> T): Flow<T> = onStart { emit(block()) }

/**
 * On successful completion of current [Flow] emit a final value
 */
fun <T> Flow<T>.successValue(value: T): Flow<T> = onCompletion { if (it == null) emit(value) }

/**
 * On completion of current [Flow] emit a final value
 */
fun <T> Flow<T>.endValue(value: T): Flow<T> = onCompletion { emit(value) }

/**
 * On error of current [Flow] emit a final value
 */
inline fun <T> Flow<T>.errorValue(crossinline block: (Throwable) -> T): Flow<T> = catch { emit(block(it)) }

/**
 * On error of current [Flow] emit a final value
 */
fun <T> Flow<T>.errorValue(value: T): Flow<T> = catch { emit(value) }

/**
 * Chain flows together that will run sequentially in the order provided
 *
 * @param links the array of [Flow] to concat
 * @return the first [Flow] element with
 */
fun <T> chainFlow(vararg links: Flow<T>): Flow<T> = flow { links.forEach { emitAll(it) } }

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

fun <T> Flow<T>.distinct(): Flow<T> {
    val elements = mutableSetOf<T>()
    return filter { element -> elements.add(element = element) }
}
