package com.treefrogapps.kotlin.coroutines.flow.processor

import com.treefrogapps.kotlin.coroutines.flow.FlowEmitter.Companion.create
import com.treefrogapps.kotlin.coroutines.flow.subscribe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

@FlowPreview
@ExperimentalCoroutinesApi
internal abstract class FlowProcessor<E>(private val broadcastChannel: BroadcastChannel<E>) {

    fun onNext(t: E): Boolean = broadcastChannel.offer(t)

    fun <T : Throwable> onError(e: T): Boolean = broadcastChannel.close(e)

    fun onComplete(): Boolean = broadcastChannel.close()

    /**
     * Create a [Flow] and open the subscription to the [BroadcastChannel]
     *
     * Unlike [kotlinx.coroutines.flow.asFlow] this has slightly different cancellation characteristics
     *
     * ### Cancellation semantics
     * Standard behaviour :
     * 1) Flow consumer is cancelled when the original channel is cancelled.
     * 2) Flow consumer completes normally when the original channel completes (~is closed) normally.
     * 3) If the flow consumer fails with an exception, subscription is cancelled.
     * Additional behaviour :
     * 4) If the [kotlinx.coroutines.Job] object is cancelled and the [kotlinx.coroutines.channels.ReceiveChannel]
     * is not closed for receive then this will cancel the subscription to the [BroadcastChannel] freeing up resources
     * and preventing a memory leak.
     *
     */
    fun asFlow(): Flow<E> = flow {
        broadcastChannel.openSubscription().run {
            emitAll(this)
            if (!isClosedForReceive) cancel()
        }
    }

    fun asFlowEmitter(): Flow<E> = create {
        asFlow().subscribe(emitterScope = this)
            .run { setCancellable { cancel() } }
    }
}