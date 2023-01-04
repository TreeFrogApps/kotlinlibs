package com.treefrogapps.kotlin.coroutines.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val shared : CoroutineDispatcher
    val main : CoroutineDispatcher
    val unconfined : CoroutineDispatcher
    val io : CoroutineDispatcher
    val single : CoroutineDispatcher
    val new : CoroutineDispatcher
}