package com.treefrogapps.kotlin.coroutines.scope

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

internal class NewCoroutineScope : CoroutineScope {

    override val coroutineContext: CoroutineContext get() =
        Executors.newSingleThreadExecutor { Thread(it, "dispatcher-new-thread") }.asCoroutineDispatcher()
}