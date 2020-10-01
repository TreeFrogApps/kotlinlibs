package com.treefrogapps.coroutines.flow.processor

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel

@ExperimentalCoroutinesApi
internal class ReplayFlowProcessor<E> : FlowProcessor<E>(BroadcastChannel<E>(Int.MAX_VALUE - 1)) {

    companion object {

        fun <E> create(): ReplayFlowProcessor<E> = ReplayFlowProcessor()

        fun <E> createWith(items: Collection<E>): ReplayFlowProcessor<E> =
                create<E>().apply { items.forEach { onNext(it) } }

    }
}