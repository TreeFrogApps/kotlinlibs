package com.treefrogapps.kotlin.coroutines.flow.processor

import com.treefrogapps.kotlin.coroutines.flow.FlowTestObserver
import com.treefrogapps.kotlin.coroutines.flow.FlowTestObserver.Companion.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ClosedSendChannelException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.EmptyCoroutineContext

@FlowPreview
@ExperimentalCoroutinesApi
class PublishFlowProcessorTest {

    private lateinit var processor: PublishFlowProcessor<Int>

    @Before fun setUp() {
        processor = PublishFlowProcessor.create()
    }

    @After fun tearDown() {
        runBlocking(EmptyCoroutineContext) {
            processor.onComplete()
        }
    }

    @Test fun `given publisher with single observer when value published then emission observed`() = runBlockingTest {
        val testObserver: FlowTestObserver<Int> = processor.asFlow().test(this)

        processor.onNext(1)

        testObserver.assert {
            assertEquals(1, first)
            assertNotComplete()
            assertNoErrors()
        }
    }

    @Test fun `given publisher with multiple observers when value published then emission observed`() = runBlockingTest {
        val testObserver: FlowTestObserver<Int> = processor.asFlow().test(this)
        val testObserver2: FlowTestObserver<Int> = processor.asFlow().test(this)

        processor.onNext(1)

        sequenceOf(testObserver, testObserver2)
            .forEach { observer ->
                observer.assert {
                    assertEquals(1, first)
                    assertNotComplete()
                    assertNoErrors()
                }
            }
    }

    @Test fun `given publisher emission when before any observers subscribed then emission not observed`() = runBlockingTest {
        processor.onNext(1)

        val testObserver: FlowTestObserver<Int> = processor.asFlow().test(this)

        testObserver.assert {
            assertNoValues()
            assertNotComplete()
            assertNoErrors()
        }
    }

    @Test fun `given publisher emission when job cancelled and new publisher emission then new emission not observed`() = runBlockingTest {
        val testObserver = processor.asFlow().test(this)
        processor.onNext(1)
        testObserver.finish()

        processor.onNext(2)

        with(testObserver) {
            assertValueCount(1)
            assertComplete()
            assertNoErrors()
        }
    }

    @Test fun `given publisher with two observers and value published when one observer finished and new emission then emission observed by one observer only`() = runBlockingTest {
        val testObserver: FlowTestObserver<Int> = processor.asFlow().test(this)
        val testObserver2: FlowTestObserver<Int> = processor.asFlow().test(this)

        processor.onNext(1)

        testObserver.finish()

        processor.onNext(2)

        with(testObserver) {
            assertValueCount(1)
            assertComplete()
            assertNoErrors()
        }

        with(testObserver2) {
            assertValueCount(2)
            assertNotComplete()
            assertNoErrors()
            finish()
        }
    }

    @Test(expected = ClosedSendChannelException::class)
    fun `given completed publisher when value published then error`() = runBlockingTest {
        processor.onComplete()
        processor.onNext(1)
    }

    @Test fun `given publisher with single observer when error published then error emission observed`() = runBlockingTest {
        val testObserver: FlowTestObserver<Int> = processor.asFlow().test(this)

        processor.onError(IllegalStateException())

        testObserver.assert {
            assertNoValues()
            assertError<IllegalStateException>()
        }
    }

    @Test fun `given publisher with no observers and error published when new observer then error emission observed`() = runBlockingTest {
        processor.onError(IllegalStateException())

        val testObserver: FlowTestObserver<Int> = processor.asFlow().test(this)

        testObserver.assert {
            assertNoValues()
            assertError<IllegalStateException>()
        }
    }

    @Test fun `given publisher with no observers and then completed when new observer immediately completed`() = runBlockingTest {
        processor.onComplete()

        val testObserver: FlowTestObserver<Int> = processor.asFlow().test(this)

        testObserver.assert {
            assertNoValues()
            assertComplete()
        }
    }
}
