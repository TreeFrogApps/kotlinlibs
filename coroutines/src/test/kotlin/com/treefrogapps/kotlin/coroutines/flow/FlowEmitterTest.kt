package com.treefrogapps.kotlin.coroutines.flow

import com.treefrogapps.kotlin.coroutines.flow.FlowTestObserver.Companion.test
import com.treefrogapps.kotlin.coroutines.flow.processor.FlowProcessor
import com.treefrogapps.kotlin.coroutines.flow.processor.PublishFlowProcessor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit.SECONDS

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class FlowEmitterTest {

    private lateinit var producer: FlowProcessor<Long>
    private lateinit var emitter: Flow<Long>

    @Before fun setup() {
        producer = PublishFlowProcessor.create()
        emitter = producer.asFlowEmitter()
    }

    @Test fun `given emitter subscribed when value published then emission observed`() = runBlockingTest {
        val testObserver: FlowTestObserver<Long> = emitter.test(this)

        producer.onNext(1L)

        testObserver.assert {
            assertEquals(1L, first)
            assertNotComplete()
            assertNoErrors()
        }
    }

    @Test fun `given emitter subscribed when multiple values published then emissions observed`() = runBlockingTest {
        val testObserver: FlowTestObserver<Long> = emitter.test(this)

        producer.onNext(1L)
        producer.onNext(2L)
        producer.onNext(3L)

        testObserver.assert {
            assertValueCount(3)
            assertEquals(1L, first)
            assertEquals(2L, second)
            assertEquals(3L, third)
            assertNotComplete()
            assertNoErrors()
        }
    }

    @Test fun `given emitter when canceled then emitter completed`() = runBlockingTest {
        val testObserver: FlowTestObserver<Long> = emitter.test(this)

        testObserver.finish()
        testObserver.assertComplete()
    }

    @Test fun `given producer cancelled then emitter completed`() = runBlockingTest {
        val testObserver: FlowTestObserver<Long> = emitter.test(this)

        producer.onComplete()
        testObserver.assertComplete()
    }

    @Test fun `given encapsulated emitter when cancelled then invokeOnClose called`() = runBlockingTest {
        var closed = false
        emitter = FlowEmitter.create({ setCancellable { closed = true } })

        emitter.test(this)
            .apply { finish() }
            .assert {
                assertComplete()
                assertTrue(closed)
            }
    }

    @Test fun `given encapsulated emitter when error then error propagated to collector`() = runBlockingTest {
        emitter = FlowEmitter.create({ throw IllegalStateException() })

        emitter.test(this).assertError<IllegalStateException>()
    }

    @Test fun `given encapsulated emitter when values produced then emissions observed`() = runBlockingTest {
        emitter = FlowEmitter.create({
                                         onNext(1L)
                                         onNext(2L)
                                     })

        emitter.test(this)
            .assert {
                assertValueCount(2)
                assertEquals(1L, first)
                assertEquals(2L, second)
            }
    }

    @Test(expected = IllegalStateException::class) fun `given encapsulated emitter when error and no error handling then error thrown`() = runBlockingTest {
        FlowEmitter.create<Long>({ throw IllegalStateException() })
            .launchIn(this)
    }

    @Test fun `given interval emitter when values produced one every second for seconds then correct emission count observed`() = runBlockingTest {
        val observer = FlowEmitter.interval(delayMillis = SECONDS.toMillis(1),
                                            initialDelayMillis = SECONDS.toMillis(1))
            .test(this)

        advanceTimeBy(SECONDS.toMillis(10))

        observer.finish()

        observer.assert {
            assertValueCount(10)
            assertNoErrors()
            assertComplete()
        }
    }

    @Test fun `given interval emitter when values produced one every second for seconds and five taken then correct emission count observed`() = runBlockingTest {
        val observer = FlowEmitter.interval(delayMillis = SECONDS.toMillis(1),
                                            initialDelayMillis = SECONDS.toMillis(1))
            .take(5)
            .test(this)

        advanceTimeBy(SECONDS.toMillis(5))

        observer.finish()

        observer.assert {
            assertValueCount(5)
            assertNoErrors()
            assertComplete()
        }
    }
}