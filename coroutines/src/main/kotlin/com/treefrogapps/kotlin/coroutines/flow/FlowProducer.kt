package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.channels.SendChannel

/**
 * Flow producer interface
 *
 * A [SendChannel] has multiple ways to produce a value,
 * thus concrete implementations abstracted through this common interface
 */
interface FlowProducer<T> {

    /**
     * Delegate produce method - implementations should use the [SendChannel]
     * to produce the supplied value.
     */
    suspend fun produce(sendChannel: SendChannel<T>, item : T)
}