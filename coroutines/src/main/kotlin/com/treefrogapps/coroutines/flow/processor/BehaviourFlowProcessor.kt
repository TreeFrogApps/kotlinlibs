package com.treefrogapps.coroutines.flow.processor

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

@FlowPreview
@ExperimentalCoroutinesApi
internal class BehaviourFlowProcessor<E>(channel: ConflatedBroadcastChannel<E>) : FlowProcessor<E>(channel) {

    companion object {

        fun <E> create(): BehaviourFlowProcessor<E> = BehaviourFlowProcessor(ConflatedBroadcastChannel())

        fun <E> createDefault(item: E): BehaviourFlowProcessor<E> = BehaviourFlowProcessor(ConflatedBroadcastChannel(item))
    }
}