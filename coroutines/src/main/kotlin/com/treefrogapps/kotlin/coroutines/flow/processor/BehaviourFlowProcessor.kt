package com.treefrogapps.kotlin.coroutines.flow.processor

import com.treefrogapps.kotlin.coroutines.flow.FlowBackpressure
import com.treefrogapps.kotlin.coroutines.flow.FlowBackpressure.BUFFER
import com.treefrogapps.kotlin.coroutines.flow.FlowProducer
import com.treefrogapps.kotlin.coroutines.flow.FlowProducerFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import sun.java2d.BackBufferCapsProvider

@FlowPreview
@ExperimentalCoroutinesApi
internal class BehaviourFlowProcessor<T>(channel: ConflatedBroadcastChannel<T>,
                                         producer: FlowProducer<T>) : FlowProcessor<T>(channel, producer) {

    companion object {

        fun <E> create(backpressure: FlowBackpressure = BUFFER): BehaviourFlowProcessor<E>
                = BehaviourFlowProcessor(ConflatedBroadcastChannel(), FlowProducerFactory.create(backpressure))

        fun <E> createDefault(item: E, backpressure: FlowBackpressure = BUFFER): BehaviourFlowProcessor<E>
                = BehaviourFlowProcessor(ConflatedBroadcastChannel(item), FlowProducerFactory.create(backpressure))
    }
}