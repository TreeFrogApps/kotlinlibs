package com.treefrogapps.kotlin.coroutines.flow.processor

import com.treefrogapps.kotlin.coroutines.flow.FlowBackpressure
import com.treefrogapps.kotlin.coroutines.flow.FlowBackpressure.BUFFER
import com.treefrogapps.kotlin.coroutines.flow.FlowProducer
import com.treefrogapps.kotlin.coroutines.flow.FlowProducerFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel

@FlowPreview
@ExperimentalCoroutinesApi
internal class PublishFlowProcessor<T>(producer: FlowProducer<T>) : FlowProcessor<T>(BroadcastChannel<T>(1), producer) {

    companion object {

        fun <E> create(backpressure: FlowBackpressure = BUFFER): PublishFlowProcessor<E> = PublishFlowProcessor(FlowProducerFactory.create(backpressure))
    }
}