package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.flow.flow

fun <T, E : Throwable> flowErrorOf(error : E) = flow<T> { throw error }