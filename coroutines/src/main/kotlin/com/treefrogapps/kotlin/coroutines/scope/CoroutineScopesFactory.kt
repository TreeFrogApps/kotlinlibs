package com.treefrogapps.kotlin.coroutines.scope

import kotlinx.coroutines.CoroutineScope

object CoroutineScopesFactory {

    val sharedMain : CoroutineScope = MainCoroutineScope()

    fun main(): CoroutineScope = MainCoroutineScope()

    fun shared() : CoroutineScope = SharedCoroutineScope()

    fun io(): CoroutineScope = IOCoroutineScope()

    fun single() : CoroutineScope = SingleCoroutineScope()

    fun new() : CoroutineScope = NewCoroutineScope()

    fun unconfined() : CoroutineScope = UnconfinedCoroutineScope()
}