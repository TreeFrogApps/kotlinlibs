package com.treefrogapps.kotlin.coroutines.flow

/**
 * Defined backpressure strategies
 *
 * Unlike RxJava which exclusively uses backpressure with threading and producer / consumer
 * contention  coroutines allows backpressure on the same thread.
 *
 * Channels both support suspending when there is contention producing / consuming values
 *
 * [BUFFER] when used any produced items that overflow the internal producer buffer will be suspended in a
 * non-blocking fashion until they can be consumed.
 *
 * [kotlinx.coroutines.channels.SendChannel.send] (producer) is suspending
 * so is [kotlinx.coroutines.channels.ReceiveChannel.receive] consumer both will negotiate the push - pull without blocking
 * note : the buffer is unbounded so take particular care where you have long running fast producer and slow consumer as you
 * might run into a [OutOfMemoryError]
 *
 * [DROP] when used any produced items that overflow the internal producer buffer will be dropped until the a slot
 * in the buffer becomes available.
 *
 * [kotlinx.coroutines.channels.SendChannel.offer] is synchronous where as [kotlinx.coroutines.channels.ReceiveChannel]
 * will suspend once it cannot receive anymore items
 */
enum class FlowBackpressure {
    BUFFER, DROP
}