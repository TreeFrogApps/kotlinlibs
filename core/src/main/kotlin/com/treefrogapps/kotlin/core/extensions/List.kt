package com.treefrogapps.kotlin.core.extensions

import kotlin.math.min

fun <T> List<T>.safeSublist(fromIndex: Int, toIndex: Int, defaultOnFail : List<T>? = null): List<T>? =
        runCatching {
            when {
                isEmpty()            -> null
                fromIndex >= toIndex -> null
                fromIndex < size     -> subList(fromIndex, min(toIndex, size))
                else                 -> null
            }
        }.getOrDefault(defaultOnFail)