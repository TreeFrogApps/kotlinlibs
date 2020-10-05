package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.channels.SendChannel

/**
 * Producer that buffers values using [SendChannel.send] if the consumer is not
 * ready to consume and the internal buffer is full.  Does so in a suspending non-blocking fashion
 */
class FlowProducerBuffered<T> : FlowProducer<T> {

    override suspend fun produce(sendChannel: SendChannel<T>, item: T) {
        sendChannel.send(item)
    }
}