package com.treefrogapps.kotlin.coroutines.flow

import com.treefrogapps.kotlin.coroutines.flow.FlowBackpressure.BUFFER
import com.treefrogapps.kotlin.coroutines.flow.FlowProducerFactory.create
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.channelFlow

/**
 * Flow emitter that allows for bridging between traditional callback interfaces to
 * produce values in the [FlowEmitterScope]
 *
 * @param block producer block which has a [FlowEmitterScope.setCancellable] block for cleanup of dependencies
 * @param backpressure back pressure strategy
 */
@ExperimentalCoroutinesApi
class FlowEmitter<T> private constructor(private val block: suspend FlowEmitterScope<T>.() -> Unit,
                                         private val backpressure: FlowBackpressure) : Flow<T> {

    companion object {

        /**
         * Factory ethod
         */
        @ExperimentalCoroutinesApi
        fun <T> create(block: suspend FlowEmitterScope<T>.() -> Unit, backpressure: FlowBackpressure = BUFFER): Flow<T> = FlowEmitter(block, backpressure)
    }

    private val wrapped: Flow<T> = channelFlow { block(FlowEmitterScope(this, create(backpressure))) }

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<T>) {
        wrapped.collect(collector)
    }
}

