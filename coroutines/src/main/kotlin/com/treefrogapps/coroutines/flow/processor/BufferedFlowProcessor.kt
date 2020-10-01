package com.treefrogapps.coroutines.flow.processor

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlin.math.max
import kotlin.math.min

@ExperimentalCoroutinesApi
internal class BufferedFlowProcessor<E>(buffer: Int) : FlowProcessor<E>(BroadcastChannel<E>(min(max(buffer, 1), Int.MAX_VALUE - 1))) {

    companion object {

        fun <E> create(buffer: Int): BufferedFlowProcessor<E> = BufferedFlowProcessor(buffer)

        fun <E> createWith(items: Collection<E>, buffer: Int = items.size): BufferedFlowProcessor<E> =
                create<E>(buffer).apply { items.forEach { onNext(it) } }
    }
}