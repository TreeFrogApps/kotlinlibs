package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Simple [FlowProducer] Factory class
 */
internal object FlowProducerFactory {

    @ExperimentalCoroutinesApi fun <T> create(backpressure: FlowBackpressure): FlowProducer<T> =
            when (backpressure) {
                FlowBackpressure.BUFFER -> FlowProducerBuffered()
                FlowBackpressure.DROP -> FlowProducerUnbuffered()
            }
}