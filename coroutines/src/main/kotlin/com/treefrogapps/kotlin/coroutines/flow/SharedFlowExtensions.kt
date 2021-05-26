package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily


fun <T> Flow<T>.asSharedFlow(scope: CoroutineScope, started: SharingStarted = Lazily, replay: Int = 1): SharedFlow<T> = this.shareIn(scope, started, replay)