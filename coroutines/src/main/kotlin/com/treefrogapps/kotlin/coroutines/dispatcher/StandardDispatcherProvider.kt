package com.treefrogapps.kotlin.coroutines.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

class StandardDispatcherProvider : DispatcherProvider {

    override val shared: CoroutineDispatcher = Dispatchers.Default
    override val main: CoroutineDispatcher = Dispatchers.Main.immediate
    override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val single: CoroutineDispatcher by lazy { Executors.newSingleThreadExecutor { Thread(it, "dispatcher-single-thread") }.asCoroutineDispatcher() }
    override val new: CoroutineDispatcher get() = Executors.newSingleThreadExecutor { Thread(it, "dispatcher-new-thread") }.asCoroutineDispatcher()
}