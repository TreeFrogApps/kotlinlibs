package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.channels.SendChannel

interface FlowProducer<T> {

    suspend fun produce(sendChannel: SendChannel<T>, item : T)
}