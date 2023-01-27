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

    @Test
    fun  `given one byte when formatted as bytes then correct string result`() {
        val expected = "1b"

        val result = (1L).formattedSize(
            sizeUnit = SizeUnit.Bytes,
            format = SizeFormat.B)

        assertEquals(expected, result)
    }

    @Test
    fun  `given one kilobyte when formatted as kilobytes then correct string result`() {
        val expected = "1Kb"

        val result = (1L).formattedSize(
            sizeUnit = SizeUnit.KiloBytes,
            format = SizeFormat.Kb)

        assertEquals(expected, result)
    }

    @Test
    fun  `given ten kilobytes when formatted as megabytes then correct string result`() {
        val expected = "0.01Mb"

        val result = (10L).formattedSize(
            sizeUnit = SizeUnit.KiloBytes,
            format = SizeFormat.Mb)

        assertEquals(expected, result)
    }

    @Test
    fun  `given one kilobyte when formatted as gigabytes then correct string result`() {
        val expected = "0.00Gb"

        val result = (1L).formattedSize(
            sizeUnit = SizeUnit.KiloBytes,
            format = SizeFormat.Gb)

        assertEquals(expected, result)
    }

    @Test
    fun  `given one megabyte when formatted as kilobytes then correct string result`() {
        val expected = "1,000Kb"

        val result = (1L).formattedSize(
            sizeUnit = SizeUnit.MegaBytes,
            format = SizeFormat.Kb)

        assertEquals(expected, result)
    }

    @Test
    fun  `given one megabyte when formatted as bytes then correct string result`() {
        val expected = "1,000,000b"

        val result = (1L).formattedSize(
            sizeUnit = SizeUnit.MegaBytes,
            format = SizeFormat.B)

        assertEquals(expected, result)
    }

    @Test
    fun  `given one megabyte as bytes when formatted as megabytes then correct string result`() {
        val expected = "1.00Mb"

        val result = (1_000_000L).formattedSize(
            sizeUnit = SizeUnit.Bytes,
            format = SizeFormat.Mb)

        assertEquals(expected, result)
    }

    @Test
    fun  `given one megabyte when formatted as megabytes then correct string result`() {
        val expected = "1.00Mb"

        val result = (1L).formattedSize(
            sizeUnit = SizeUnit.MegaBytes,
            format = SizeFormat.Mb)

        assertEquals(expected, result)
    }

    @Test
    fun  `given ten megabytes when formatted as gigabytes then correct string result`() {
        val expected = "0.01Gb"

        val result = (10L).formattedSize(
            sizeUnit = SizeUnit.MegaBytes,
            format = SizeFormat.Gb)

        assertEquals(expected, result)
    }

    @Test
    fun  `given one gigabyte when formatted as kilobytes then correct string result`() {
        val expected = "1,000,000Kb"

        val result = (1L).formattedSize(
            sizeUnit = SizeUnit.GigaBytes,
            format = SizeFormat.Kb)

        assertEquals(expected, result)
    }

    @Test
    fun  `given one gigabyte when formatted as megabytes then correct string result`() {
        val expected = "1,000.00Mb"

        val result = (1L).formattedSize(
            sizeUnit = SizeUnit.GigaBytes,
            format = SizeFormat.Mb)

        assertEquals(expected, result)
    }

    @Test
    fun  `given one gigabyte when formatted as gigabytes then correct string result`() {
        val expected = "1.00Gb"

        val result = (1L).formattedSize(
            sizeUnit = SizeUnit.GigaBytes,
            format = SizeFormat.Gb)

        assertEquals(expected, result)
    }
}