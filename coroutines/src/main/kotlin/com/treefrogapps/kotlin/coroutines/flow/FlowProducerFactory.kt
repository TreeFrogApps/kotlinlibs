package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope

internal object FlowProducerFactory {

    @ExperimentalCoroutinesApi fun <T> create(backpressure: FlowBackpressure) : FlowProducer<T> =
        when(backpressure) {
            FlowBackpressure.BUFFER -> FlowProducerBuffered()
            FlowBackpressure.DROP -> FlowProducerUnbuffered()
        }
}