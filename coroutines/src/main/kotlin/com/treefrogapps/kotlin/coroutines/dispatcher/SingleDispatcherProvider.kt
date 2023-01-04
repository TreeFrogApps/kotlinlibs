package com.treefrogapps.kotlin.coroutines.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

class SingleDispatcherProvider(dispatcher: CoroutineDispatcher) : DispatcherProvider {

    companion object {
        fun CoroutineDispatcher.asSingleDispatcherProvider() : DispatcherProvider = SingleDispatcherProvider(dispatcher = this)
    }

    override val shared: CoroutineDispatcher = dispatcher
    override val main: CoroutineDispatcher = dispatcher
    override val unconfined: CoroutineDispatcher = dispatcher
    override val io: CoroutineDispatcher = dispatcher
    override val single: CoroutineDispatcher = dispatcher
    override val new: CoroutineDispatcher = dispatcher
}