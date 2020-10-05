package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.channels.SendChannel

class FlowProducerUnbuffered<T> : FlowProducer<T> {

    override suspend fun produce(sendChannel: SendChannel<T>, item: T) {
        sendChannel.offer(item)
    }
}