package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.channels.SendChannel

/**
 * Producer that drops values using [SendChannel.offer] if the consumer is not
 * ready to consume and the internal buffer is full.  Called is a synchronous way
 */
class FlowProducerUnbuffered<T> : FlowProducer<T> {

    override suspend fun produce(sendChannel: SendChannel<T>, item: T) {
        sendChannel.offer(item)
    }
}