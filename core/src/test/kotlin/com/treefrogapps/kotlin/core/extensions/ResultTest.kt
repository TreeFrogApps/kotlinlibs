package com.treefrogapps.kotlin.core.extensions

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class ResultTest {


    @Test
    fun `when result success onComplete called`() {
        var result = 0

        Result.success(value = "test")
            .onComplete { result = 1 }

        assertEquals(1, result)
    }

    @Test
    fun `when result failure onComplete called`() {
        var result = 0

        Result.failure<String>(IllegalArgumentException())
            .onComplete { result = 1 }

        assertEquals(1, result)
    }

    @Test
    fun `given catchable exception list when runCatchable then exception caught with failure result`() {
        val catchable = listOf(IOException::class.java)

        val result = runCatchable(catchable = catchable) {
            throw IOException()
        }

        assertTrue(result.isFailure)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `given non catchable exception list when runCatchable then exception not caught`() {
        val catchable = listOf(
            IOException::class.java,
            IllegalStateException::class.java)

        runCatchable(catchable = catchable) {
            throw IllegalArgumentException()
        }
    }

    @Test
    fun `given catchable exception when runCatchable then exception caught with failure result`() {
        val result = runCatchable(
            IOException::class.java,
            IllegalArgumentException::class.java
        ) {
            throw IOException()
        }

        assertTrue(result.isFailure)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `given non catchable exception when runCatchable then exception not caught`() {
        val catchable = listOf(IOException::class.java)

        runCatchable(catchable = catchable) {
            throw IllegalArgumentException()
        }
    }
}