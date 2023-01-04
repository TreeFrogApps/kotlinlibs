package com.treefrogapps.kotlin.coroutines.scope

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

internal class UnconfinedCoroutineScope : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Unconfined + SupervisorJob()
}
