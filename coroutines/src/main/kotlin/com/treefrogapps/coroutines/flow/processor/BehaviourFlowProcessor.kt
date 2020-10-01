package com.treefrogapps.coroutines.flow.processor

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED

@ExperimentalCoroutinesApi
internal class BehaviourFlowProcessor<E> : FlowProcessor<E>(BroadcastChannel<E>(CONFLATED)) {

    companion object {

        fun <E> create(): BehaviourFlowProcessor<E> = BehaviourFlowProcessor()

        fun <E> createDefault(item: E): BehaviourFlowProcessor<E> = create<E>().apply { onNext(item) }
    }
}