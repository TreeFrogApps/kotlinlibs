package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.shareIn


fun <T> Flow<T>.asSharedFlow(scope: CoroutineScope, started: SharingStarted = Lazily, replay: Int = 1): SharedFlow<T> = this.shareIn(scope, started, replay)