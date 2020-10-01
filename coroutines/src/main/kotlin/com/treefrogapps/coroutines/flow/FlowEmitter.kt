package com.treefrogapps.coroutines.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
internal class FlowEmitter<T> constructor(private val block: ProducerScope<T>.() -> Unit) : Flow<T> {

    private val wrapped: Flow<T> = channelFlow { block(this)  }

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<T>) {
        wrapped.collect(collector)
    }
}

@ExperimentalCoroutinesApi
fun <T> emitter(block: ProducerScope<T>.() -> Unit) : Flow<T> = FlowEmitter(block)