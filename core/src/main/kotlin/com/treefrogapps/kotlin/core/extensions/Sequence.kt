package com.treefrogapps.kotlin.core.extensions

inline fun <T, E, R> Sequence<Map.Entry<T, E>>.foldToSet(block: (Map.Entry<T, E>) -> R): Set<R> =
        fold(HashSet()) { set, entry -> set.add(block(entry)); set }

inline fun <T, E, R> Sequence<Map.Entry<T, E>>.foldToList(block: (Map.Entry<T, E>) -> R): List<R> =
        foldToCollection(mutableListOf(), block)

inline fun <A : MutableCollection<R>, T, E, R> Sequence<Map.Entry<T, E>>.foldToCollection(accumulator: A, block: (Map.Entry<T, E>) -> R): A =
        fold(accumulator) { acc, entry -> acc.add(block(entry)); acc }

inline fun <K, V> Sequence<V>.foldToMap(func: (MutableMap<K, V>, V) -> Unit): Map<K, V> =
        this.fold(HashMap()) { map, item -> func(map, item); map }

inline fun <K, V> Sequence<V>.foldToMap(keyConverter: (V) -> K): Map<K, V> =
        this.fold(HashMap()) { map, item -> map[keyConverter(item)] = item; map }
