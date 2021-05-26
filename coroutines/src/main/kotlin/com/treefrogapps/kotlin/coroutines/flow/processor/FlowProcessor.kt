package com.treefrogapps.kotlin.coroutines.flow.processor

import com.treefrogapps.kotlin.coroutines.flow.FlowEmitter.Companion.create
import com.treefrogapps.kotlin.coroutines.flow.FlowObserver
import com.treefrogapps.kotlin.coroutines.flow.FlowProducer
import com.treefrogapps.kotlin.coroutines.flow.subscribe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

@ObsoleteCoroutinesApi
abstract class FlowProcessor<T>(private val broadcastChannel: BroadcastChannel<T>,
                                private val producer: FlowProducer<T>) : FlowObserver<T> {

    override suspend fun onNext(t: T) = producer.produce(broadcastChannel, t)

    override suspend fun onError(e: Throwable) {
        broadcastChannel.close(e)
    }

    override suspend fun onComplete() {
        broadcastChannel.close()
    }

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
    @ExperimentalCoroutinesApi
    fun asFlow(): Flow<T> = flow {
        broadcastChannel.openSubscription().run {
            emitAll(this)
            if (!isClosedForReceive) cancel()
        }
    }

    @ExperimentalCoroutinesApi
    fun asFlowEmitter(): Flow<T> =
            create({ asFlow().subscribe(emitterScope = this).run { setCancellable { cancel() } } })
}