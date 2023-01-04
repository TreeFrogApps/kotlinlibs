package com.treefrogapps.kotlin.coroutines.scope

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

internal class SingleCoroutineScope : CoroutineScope {

    companion object {
        private val single by lazy { Executors.newSingleThreadExecutor { Thread(it, "dispatcher-single-thread") }.asCoroutineDispatcher() }
    }

    override val coroutineContext: CoroutineContext = single + SupervisorJob()
}