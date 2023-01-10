package com.treefrogapps.kotlin.core.extensions

import java.util.concurrent.atomic.AtomicReference
import kotlin.reflect.KProperty

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T> AtomicReference<T>.getValue(thisRef: Any?, property: KProperty<*>): T = get()

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T> AtomicReference<T>.setValue(thisObj: Any?, property: KProperty<*>, value: T) {
    set(value)
}

fun <T> atomicReference(initial: T?): AtomicReference<T?> = AtomicReference(initial)