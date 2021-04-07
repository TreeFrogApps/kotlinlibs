package com.treefrogapps.kotlin.coroutines.flow

import com.treefrogapps.kotlin.coroutines.flow.FlowTestObserver.Companion.test
import com.treefrogapps.kotlin.coroutines.flow.extensions.combine
import com.treefrogapps.kotlin.coroutines.flow.extensions.first
import com.treefrogapps.kotlin.coroutines.flow.extensions.firstIf
import com.treefrogapps.kotlin.coroutines.flow.extensions.firstOrDefault
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class FlowExtensionsTest {


    @Test fun `whenFlowWithMultipleEmissions whenFirst thenOnlyObserveFirstItem`() = runBlockingTest {
        flowOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
            .first()
            .test(this)
            .assert {
                assertNoErrors()
                assertComplete()
                assertValueCount(1)
                assertEquals(1, first)
            }
    }

    @Test fun `whenFlowWithMultipleEmissions whenFirstIfWithTruePredicate thenOnlyObserveFirstItem`() = runBlockingTest {
        flowOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
            .firstIf { it == 1 }
            .test(this)
            .assert {
                assertNoErrors()
                assertComplete()
                assertValueCount(1)
                assertEquals(1, first)
            }
    }

    @Test fun `whenFlowWithMultipleEmissions whenFirstIfWithFalsePredicate thenObserveNoItem`() = runBlockingTest {
        flowOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
            .firstIf { it == -1 }
            .test(this)
            .assert {
                assertNoErrors()
                assertComplete()
                assertValueCount(0)
            }
    }

    @Test fun `whenFlowWithMultipleEmissions whenFirstIfWithFalsePredicateAndDefault thenObserveDefaultItem`() = runBlockingTest {
        val default = 10
        flowOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
            .firstOrDefault(default) { it == -1 }
            .test(this)
            .assert {
                assertNoErrors()
                assertComplete()
                assertValueCount(1)
                assertEquals(default, first)
            }
    }
}