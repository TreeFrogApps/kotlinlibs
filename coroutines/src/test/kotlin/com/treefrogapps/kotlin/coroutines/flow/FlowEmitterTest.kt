package com.treefrogapps.kotlin.coroutines.flow

import com.treefrogapps.kotlin.coroutines.flow.FlowTestObserver.Companion.test
import com.treefrogapps.kotlin.coroutines.flow.processor.FlowProcessor
import com.treefrogapps.kotlin.coroutines.flow.processor.PublishFlowProcessor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@FlowPreview
@ExperimentalCoroutinesApi
class FlowEmitterTest {

    private lateinit var producer: FlowProcessor<Int>
    private lateinit var emitter: Flow<Int>

    @Before fun setup() {
        producer = PublishFlowProcessor.create()
        emitter = producer.asFlowEmitter()
    }

    @Test fun name() = runBlockingTest {
        FlowEmitter.create<Int>({
                                    (0 until 1_000).forEach { onNext(it) }
                                    onComplete()
                                    setCancellable { println("Cancelled") }
                                }, FlowBackpressure.BUFFER)
            .subscribe(this, object : FlowObserver<Int> {
                override suspend fun onNext(t: Int) {
                    println("Mark next : $t")
                }

                override suspend fun onError(e: Throwable) {
                    println(e)
                }

                override suspend fun onComplete() {
                    println("Completed")
                }

            })
    }

    @Test fun `given emitter subscribed when value published then emission observed`() = runBlockingTest {
        val testObserver: FlowTestObserver<Int> = emitter.test(this)

        producer.onNext(1)

        testObserver.assert {
            assertEquals(1, first)
            assertNotComplete()
            assertNoErrors()
        }
    }

    @Test fun `given emitter subscribed when multiple values published then emissions observed`() = runBlockingTest {
        val testObserver: FlowTestObserver<Int> = emitter.test(this)

        producer.onNext(1)
        producer.onNext(2)
        producer.onNext(3)

        testObserver.assert {
            assertValueCount(3)
            assertEquals(1, first)
            assertEquals(2, second)
            assertEquals(3, third)
            assertNotComplete()
            assertNoErrors()
        }
    }

    @Test fun `given emitter when canceled then emitter completed`() = runBlockingTest {
        val testObserver: FlowTestObserver<Int> = emitter.test(this)

        testObserver.finish()
        testObserver.assertComplete()
    }

    @Test fun `given producer cancelled then emitter completed`() = runBlockingTest {
        val testObserver: FlowTestObserver<Int> = emitter.test(this)

        producer.onComplete()
        testObserver.assertComplete()
    }

    @Test fun `given encapsulated emitter when cancelled then invokeOnClose called`() = runBlockingTest {
        var closed = false
        emitter = FlowEmitter.create ({ setCancellable { closed = true } })

        emitter.test(this)
            .apply { finish() }
            .assert {
                assertComplete()
                assertTrue(closed)
            }
    }

    @Test fun `given encapsulated emitter when error then error propagated to collector`() = runBlockingTest {
        emitter = FlowEmitter.create ({ throw IllegalStateException() })

        emitter.test(this).assertError<IllegalStateException>()
    }

    @Test fun `given encapsulated emitter when values produced then emissions observed`() = runBlockingTest {
        emitter = FlowEmitter.create ({
            onNext(1)
            onNext(2)
        })

        emitter.test(this)
            .assert {
                assertValueCount(2)
                assertEquals(1, first)
                assertEquals(2, second)
            }
    }

    @Test(expected = IllegalStateException::class) fun `given encapsulated emitter when error and no error handling then error thrown`() = runBlockingTest {
        FlowEmitter.create<Int> ({ throw IllegalStateException() })
            .launchIn(this)
    }
}