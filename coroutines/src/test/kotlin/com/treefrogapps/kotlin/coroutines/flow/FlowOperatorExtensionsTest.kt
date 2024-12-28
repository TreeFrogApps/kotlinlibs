package com.treefrogapps.kotlin.coroutines.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.testTimeSource
import kotlin.test.Test
import kotlin.test.assertEquals

class FlowOperatorExtensionsTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given flow throttle, When flow throttle not met, Then throttle timeout honoured with single emission`() = runTest {
        // Setup
        val timeout = 50L
        val timeDelay = timeout / 2
        val values = mutableListOf<Int>()
        val emitter = mutableSharedFlow<Int>()

        // Given
        backgroundScope.launch {
            emitter
                .throttle(timeoutMillis = timeout, timeSource = testTimeSource)
                .collect { values.add(it) }
        }
        runCurrent()

        // When
        emitter.emit(value = 0)
        advanceTimeBy(delayTimeMillis = timeDelay)
        emitter.emit(value = 1)
        runCurrent()

        // Then
        assertEquals(expected = 1, actual = values.size)
        assertEquals(expected = 0, actual = values[0])
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given flow throttle, When flow throttle met, Then throttle timeout honoured with multiple emissions`() = runTest {
        // Setup
        val timeout = 50L
        val timeDelay = timeout + 1L
        val values = mutableListOf<Int>()
        val emitter = mutableSharedFlow<Int>()

        // Given
        backgroundScope.launch {
            emitter
                .throttle(timeoutMillis = timeout, timeSource = testTimeSource)
                .collect { values.add(it) }
        }
        runCurrent()

        // When
        emitter.emit(value = 0)
        advanceTimeBy(delayTimeMillis = timeDelay)
        emitter.emit(value = 1)
        runCurrent()

        // Then
        assertEquals(expected = 2, actual = values.size)
        assertEquals(expected = 0, actual = values[0])
        assertEquals(expected = 1, actual = values[1])
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given flow throttle, When flow throttle met in two intervals, Then throttle timeout honoured with multiple emissions`() = runTest {
        // Setup
        val timeout = 50L
        val timeDelay = timeout + 1L
        val values = mutableListOf<Int>()
        val emitter = mutableSharedFlow<Int>()

        // Given
        backgroundScope.launch {
            emitter
                .throttle(timeoutMillis = timeout, timeSource = testTimeSource)
                .collect { values.add(it) }
        }
        runCurrent()

        // When
        emitter.emit(value = 0)
        advanceTimeBy(delayTimeMillis = timeDelay)
        emitter.emit(value = 1)
        advanceTimeBy(delayTimeMillis = timeDelay)
        emitter.emit(value = 2)
        advanceTimeBy(delayTimeMillis = timeDelay)
        emitter.emit(value = 3)
        emitter.emit(value = 4)
        advanceTimeBy(delayTimeMillis = timeDelay / 2)
        emitter.emit(value = 5)
        runCurrent()

        // Then
        assertEquals(expected = 4, actual = values.size)
        assertEquals(expected = 0, actual = values[0])
        assertEquals(expected = 1, actual = values[1])
        assertEquals(expected = 2, actual = values[2])
        assertEquals(expected = 3, actual = values[3])
    }
}
