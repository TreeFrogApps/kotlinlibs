package com.treefrogapps.kotlin.core.extensions

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class StringKtTest {

    @Test
    fun `When first string empty and same as string2, Then null result`() {
        // When
        val string = emptyString()

        val actual = firstCharIfDifferent(string = string, string2 = string)
        // Then
        assertNull(actual)
    }

    @Test
    fun `When first string not empty and same as string2, Then null result`() {
        // When
        val string = "T"

        val actual = firstCharIfDifferent(string = string, string2 = string)
        // Then
        assertNull(actual)
    }

    @Test
    fun `When first string not empty and string2 uppercase, Then null result`() {
        // When
        val string = "t"
        val string2 = "T"

        val actual = firstCharIfDifferent(string = string, string2 = string2)

        // Then
        assertNull(actual)
    }

    @Test
    fun `When first string empty and string2 not empty, Then string2 first char result`() {
        // When
        val string = ""
        val string2 = "A"
        val expected = string2.first()

        val actual = firstCharIfDifferent(string = string, string2 = string2)
        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `When first string empty and string2 lowercase and not empty, Then string2 first char uppercase result`() {
        // When
        val string = ""
        val string2 = "a"
        val expected = string2.first().uppercaseChar()

        val actual = firstCharIfDifferent(string = string, string2 = string2)
        // Then
        assertEquals(expected, actual)
    }
}