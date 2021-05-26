package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

fun <T> Flow<T>.asNullableStateFlow(scope : CoroutineScope, started: SharingStarted = Lazily, initialState : T? = null) : StateFlow<T?> = this.stateIn(scope, started, initialState)

fun <T> Flow<T>.asStateFlow(scope : CoroutineScope, started: SharingStarted = Lazily, initialState : T) : StateFlow<T> = this.stateIn(scope, started, initialState)