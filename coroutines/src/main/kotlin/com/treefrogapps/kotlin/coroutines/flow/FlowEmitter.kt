package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.channelFlow

@ExperimentalCoroutinesApi
class FlowEmitter<T> private constructor(private val block: ProducerScope<T>.() -> Unit) : Flow<T> {

    companion object {
        @ExperimentalCoroutinesApi
        fun <T> create(block: ProducerScope<T>.() -> Unit): Flow<T> = FlowEmitter(block)
    }

    private val wrapped: Flow<T> = channelFlow { block(this) }

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<T>) {
        wrapped.collect(collector)
    }
}

