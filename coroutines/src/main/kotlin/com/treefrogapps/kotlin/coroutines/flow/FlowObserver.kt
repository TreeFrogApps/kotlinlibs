package com.treefrogapps.kotlin.coroutines.flow

/**
 * Flow Observer class to receive Flow events
 */
interface FlowObserver<T> {

    suspend fun onNext(t: T)

    suspend fun onError(e: Throwable)

    suspend fun onComplete()
}