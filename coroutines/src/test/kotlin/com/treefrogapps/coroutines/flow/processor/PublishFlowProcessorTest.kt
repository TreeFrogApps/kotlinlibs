package com.treefrogapps.coroutines.flow.processor

import com.treefrogapps.coroutines.flow.FlowTestObserver
import com.treefrogapps.coroutines.flow.FlowTestObserver.Companion.test
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@Suppress("EXPERIMENTAL_API_USAGE")
class PublishFlowProcessorTest {

    private lateinit var processor: PublishFlowProcessor<Int>

    @Before fun setUp() {
        processor = PublishFlowProcessor.create()
    }

    @After fun tearDown() {
        processor.onComplete()
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
}
