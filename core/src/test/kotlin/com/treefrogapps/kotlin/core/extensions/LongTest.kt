package com.treefrogapps.kotlin.core.extensions

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.TimeUnit

class LongTest {

    @Test
    fun `given negative one second as millis when formatted as seconds then correct string result`() {
        val expected = "-1"

        val result = (-1_000L).formattedDuration(
            timeUnit = TimeUnit.MILLISECONDS,
            format = DurationFormat.Seconds)

        assertEquals(expected, result)
    }

    @Test
    fun `given one second as millis when formatted as seconds then correct string result`() {
        val expected = "1"

        val result = 1_000L.formattedDuration(
            timeUnit = TimeUnit.MILLISECONDS,
            format = DurationFormat.Seconds)

        assertEquals(expected, result)
    }

    @Test
    fun `given negative one minute as millis when formatted as minutes and seconds then correct string result`() {
        val expected = "-1:00"

        val result = (-1_000L * 60L).formattedDuration(
            timeUnit = TimeUnit.MILLISECONDS,
            format = DurationFormat.MinutesSeconds)

        assertEquals(expected, result)
    }

    @Test
    fun `given one minute as millis when formatted as minutes and seconds then correct string result`() {
        val expected = "1:00"

        val result = (1_000L * 60L).formattedDuration(
            timeUnit = TimeUnit.MILLISECONDS,
            format = DurationFormat.MinutesSeconds)

        assertEquals(expected, result)
    }

    @Test
    fun `given negative one hour as millis when formatted as hours, minutes and seconds then correct string result`() {
        val expected = "-1:00:00"

        val result = (-1_000L * 60L * 60L).formattedDuration(
            timeUnit = TimeUnit.MILLISECONDS,
            format = DurationFormat.HoursMinutesSeconds)

        assertEquals(expected, result)
    }

    @Test
    fun `given one hour as millis when formatted as hours, minutes and seconds then correct string result`() {
        val expected = "1:00:00"

        val result = (1_000L * 60L * 60L).formattedDuration(
            timeUnit = TimeUnit.MILLISECONDS,
            format = DurationFormat.HoursMinutesSeconds)

        assertEquals(expected, result)
    }
}