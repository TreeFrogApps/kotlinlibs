package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

/**
 * Test observer class for asserting flows
 */
@Suppress("EXPERIMENTAL_API_USAGE")
class FlowTestObserver<T>(scope: CoroutineScope, flow: Flow<T>) {

    companion object {
        fun <T> Flow<T>.test(scope: CoroutineScope): FlowTestObserver<T> = FlowTestObserver(scope, this)
    }

    private val values: MutableList<T> = mutableListOf()
    private val job: Job =
            flow.onEach(values::add)
                .onCompletion { isComplete = true }
                .catch { error = it }
                .launchIn(scope)

    private var isComplete: Boolean = false
    var error: Throwable? = null

    val first: T? get() = values.firstOrNull()
    val second: T? get() = values.elementAtOrNull(1)
    val third: T? get() = values.elementAtOrNull(2)

    fun assertValueCount(count: Int) {
        if (values.size != count) throw AssertionError("Values count mismatch expected $count, was ${values.size}")
    }

    fun assertNoValues() {
        if (values.size > 0) throw AssertionError("Expected no values, but have ${values.size}")
    }

    fun assertComplete() {
        if (!isComplete) throw AssertionError("Not Completed")
    }

    fun assertNotComplete() {
        if (isComplete) throw AssertionError("Completed")
    }

    fun assertNoErrors() {
        if (error != null) throw AssertionError("Error occurred")
    }

    inline fun <reified T : Throwable> assertError() {
        if (error == null) throw AssertionError("No Error")
        if (error !is T) throw AssertionError("Errors do not match expected ${T::class.java}, but is : $error!!::class.java}")
    }

    fun assert(block: FlowTestObserver<T>.() -> Unit) {
        block(this)
        job.cancel()
    }

    fun runAndAssert(run: () -> Unit, block: FlowTestObserver<T>.() -> Unit) {
        run()
        block(this)
        job.cancel()
    }

    fun finish() {
        if (job.isActive) job.cancel()
    }
}



