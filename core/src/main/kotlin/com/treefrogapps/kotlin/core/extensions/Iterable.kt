package com.treefrogapps.kotlin.core.extensions

inline fun <K, V> Iterable<V>.foldToMap(func: (MutableMap<K, V>, V) -> Unit): Map<K, V> =
    this.fold(HashMap()) { map, item -> func(map, item); map }

inline fun <K, V> Iterable<V>.foldToMap(keyConverter: (V) -> K): Map<K, V> =
    this.fold(HashMap()) { map, item -> map[keyConverter(item)] = item; map }