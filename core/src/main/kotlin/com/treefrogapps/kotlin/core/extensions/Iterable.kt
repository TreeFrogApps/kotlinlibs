package com.treefrogapps.kotlin.core.extensions

inline fun <T> Iterable<T>.foldToMap(func: (MutableMap<Any, Any>, T) -> Unit): Map<Any, Any> = this.fold(HashMap(), { map, item -> func(map, item); map })
