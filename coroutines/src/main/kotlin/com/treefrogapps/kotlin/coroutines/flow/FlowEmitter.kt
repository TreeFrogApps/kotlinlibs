package com.treefrogapps.kotlin.coroutines.flow

import com.treefrogapps.kotlin.coroutines.flow.FlowBackpressure.BUFFER
import com.treefrogapps.kotlin.coroutines.flow.FlowBackpressure.DROP
import com.treefrogapps.kotlin.coroutines.flow.FlowProducerFactory.create
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive

/**
 * Flow emitter that allows for bridging between traditional callback interfaces to
 * produce values in the [FlowEmitterScope]
 *
 * @param block producer block which has a [FlowEmitterScope.setCancellable] block for cleanup of dependencies
 * @param backpressure back pressure strategy
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FlowEmitter<T> private constructor(private val block: suspend FlowEmitterScope<T>.() -> Unit,
                                         private val backpressure: FlowBackpressure) : Flow<T> {

    companion object {

        /**
         * Factory method
         */
        fun <T> create(block: suspend FlowEmitterScope<T>.() -> Unit, backpressure: FlowBackpressure = BUFFER): Flow<T> = FlowEmitter(block, backpressure)

        fun interval(delayMillis: Long, initialDelayMillis: Long = 0L): Flow<Long> = create(
                {
                    delay(initialDelayMillis)
                    var i = 0L
                    while (isActive) {
                        onNext(++i)
                        delay(delayMillis)
                    }
                }, DROP)

    }

    private val wrapped: Flow<T> = channelFlow { block(FlowEmitterScope(this, create(backpressure))) }

    @OptIn(InternalCoroutinesApi::class)
    override suspend fun collect(collector: FlowCollector<T>) {
        wrapped.collect(collector)
    }
}

