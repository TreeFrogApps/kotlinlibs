package com.treefrogapps.kotlin.core.extensions

fun <T, E, R> Sequence<Map.Entry<T, E>>.foldToSet(block: (Map.Entry<T, E>) -> R): Set<R> =
        fold(HashSet(), { set, entry -> set.add(block(entry)); set })

fun <T, E, R> Sequence<Map.Entry<T, E>>.foldToList(block: (Map.Entry<T, E>) -> R): List<R> =
        foldToCollection(mutableListOf(), block)

fun <A : MutableCollection<R>, T, E, R> Sequence<Map.Entry<T, E>>.foldToCollection(accumulator: A, block: (Map.Entry<T, E>) -> R): A =
        fold(accumulator, { acc, entry -> acc.add(block(entry)); acc })
