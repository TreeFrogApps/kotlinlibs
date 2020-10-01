package com.treefrogapps.coroutines.flow.processor

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel

@ExperimentalCoroutinesApi
internal class PublishFlowProcessor<E> : FlowProcessor<E>(BroadcastChannel<E>(1)) {

    companion object {

        fun <E> create(): PublishFlowProcessor<E> = PublishFlowProcessor()
    }
}