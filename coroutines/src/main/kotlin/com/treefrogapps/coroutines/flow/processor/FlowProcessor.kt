package com.treefrogapps.coroutines.flow.processor

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@ExperimentalCoroutinesApi
abstract class FlowProcessor<E>(private val broadcastChannel: BroadcastChannel<E>) {

    fun onNext(item: E) = broadcastChannel.offer(item)

    fun <T : Throwable> onError(error: T): Boolean = broadcastChannel.close(error)

    fun onComplete(): Boolean = broadcastChannel.close()

    @FlowPreview
    fun asFlow(): Flow<E> = broadcastChannel.asFlow()
}