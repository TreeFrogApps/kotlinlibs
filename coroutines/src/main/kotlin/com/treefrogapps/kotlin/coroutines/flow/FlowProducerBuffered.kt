package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.channels.SendChannel

class FlowProducerBuffered<T> : FlowProducer<T> {

    override suspend fun produce(sendChannel: SendChannel<T>, item: T) {
        sendChannel.send(item)
    }
}