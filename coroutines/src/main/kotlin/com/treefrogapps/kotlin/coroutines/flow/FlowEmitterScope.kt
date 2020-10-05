package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class FlowEmitterScope<T> constructor(private val producerScope: ProducerScope<T>,
                                      private val producer: FlowProducer<T>) : FlowObserver<T>, CoroutineScope {


    override val coroutineContext: CoroutineContext = producerScope.coroutineContext

    override suspend fun onNext(t: T) {
        producer.produce(producerScope, t)
    }

    override suspend fun onError(e: Throwable) {
        producerScope.close(e)
    }

    override suspend fun onComplete() {
        producerScope.close()
    }

    suspend fun setCancellable(block: () -> Unit) {
        producerScope.awaitClose { block() }
    }
}