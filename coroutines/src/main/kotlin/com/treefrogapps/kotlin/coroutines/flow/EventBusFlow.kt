package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * Thin delegate class around a [MutableSharedFlow] that inherits from [SharedFlow].  Created for concrete use cases of
 * wanting to use an event bus / observer pattern
 */
class EventBusFlow<T> internal constructor(private val eventBus: MutableSharedFlow<T>) : SharedFlow<T> by eventBus {

    companion object {
        fun <T> MutableSharedFlow<T>.asFlowEventBus(): EventBusFlow<T> = EventBusFlow(this)

        fun <T> create(replay: Int = 0): EventBusFlow<T> = EventBusFlow(MutableSharedFlow(replay = replay))

        fun <T> CoroutineScope.sendEvent(bus: EventBusFlow<T>, event: T): Job = launch { bus.event(event) }

        fun <T> EventBusFlow<T>.sendEvent(scope: CoroutineScope, event: T): Job = scope.launch { event(event) }
    }

    suspend fun event(event: T) = eventBus.emit(event)

    fun tryEvent(event: T): Boolean = eventBus.tryEmit(event)
}