package com.treefrogapps.kotlin.coroutines.flow.processor

import com.treefrogapps.kotlin.coroutines.flow.FlowTestObserver
import com.treefrogapps.kotlin.coroutines.flow.FlowTestObserver.Companion.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.EmptyCoroutineContext

/**
 * [BehaviourFlowProcessor] specific tests (replay 1 behaviour).
 * Base [FlowProcessor] behavioural tests covered in [PublishFlowProcessorTest] class
 */
@FlowPreview
@ExperimentalCoroutinesApi
class BehaviourFlowProcessorTest {

    private lateinit var processor: BehaviourFlowProcessor<Int>

    @Before fun setUp() {
        processor = BehaviourFlowProcessor.create()
    }

    @After fun tearDown() {
        runBlocking(EmptyCoroutineContext) {
            processor.onComplete()
        }
    }

    @Test fun `given publisher emission and before any observers subscribed when multiple new observers then previous emission observed`() = runBlockingTest {
        processor.onNext(1)

        val testObserver: FlowTestObserver<Int> = processor.asFlow().test(this)
        val testObserver2: FlowTestObserver<Int> = processor.asFlow().test(this)

        sequenceOf(testObserver, testObserver2).forEach { observer ->
            observer.assert {
                assertValueCount(1)
                assertNotComplete()
                assertNoErrors()
            }
        }
    }

    @Test fun `given multiple publisher emissions and before any observers subscribed when multiple new observers then latest emission observed`() = runBlockingTest {
        processor.onNext(1)
        processor.onNext(2)

        val testObserver: FlowTestObserver<Int> = processor.asFlow().test(this)
        val testObserver2: FlowTestObserver<Int> = processor.asFlow().test(this)

        sequenceOf(testObserver, testObserver2).forEach { observer ->
            observer.assert {
                assertValueCount(1)
                assertEquals(2, first)
                assertNotComplete()
                assertNoErrors()
            }
        }
    }

    @Test fun `given publisher emission and then one observer when new emission then another observer then latest emission observed by both`() = runBlockingTest {
        processor.onNext(1)

        val testObserver: FlowTestObserver<Int> = processor.asFlow().test(this)

        processor.onNext(2)

        val testObserver2: FlowTestObserver<Int> = processor.asFlow().test(this)

        testObserver.assert {
            assertValueCount(2)
            assertEquals(1, first)
            assertEquals(2, second)
            assertNotComplete()
            assertNoErrors()
        }

        testObserver2.assert {
            assertValueCount(1)
            assertEquals(2, first)
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

    @Test fun `given publisher with default when new observers subscribed then default value observed`() = runBlockingTest {
        processor = BehaviourFlowProcessor.createDefault(1)

        val testObserver: FlowTestObserver<Int> = processor.asFlow().test(this)
        val testObserver2: FlowTestObserver<Int> = processor.asFlow().test(this)

        sequenceOf(testObserver, testObserver2).forEach { observer ->
            observer.assert {
                assertValueCount(1)
                assertEquals(1, first)
                assertNotComplete()
                assertNoErrors()
            }
        }
    }
}