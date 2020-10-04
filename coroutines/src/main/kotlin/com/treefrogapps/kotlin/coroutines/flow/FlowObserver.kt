package com.treefrogapps.kotlin.coroutines.flow

interface FlowObserver<T> {

    fun onNext(t: T)

    suspend fun onError(e: Throwable)

    suspend fun onComplete()
}