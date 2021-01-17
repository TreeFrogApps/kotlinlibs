package com.treefrogapps.kotlin.core.extensions

import org.junit.Assert.*
import org.junit.Test

class ListTest {

    @Test fun `whenEmptyList thenNullResult`() {
        val list = listOf<Any>()

        assertNull(list.safeSublist(0, 10))
    }

    @Test fun `givenNonEmptyList whenFromIndexGreaterThanToIndex thenNullResult`() {
        val list = listOf(Any(), Any())

        assertNull(list.safeSublist(2, 1))
    }

    @Test fun `givenNonEmptyList whenFromIndexGreaterThanSize thenNullResult`() {
        val list = listOf(Any(), Any())

        assertNull(list.safeSublist(3, 4))
    }

    @Test fun `givenNonEmptyList whenValidSublistSameAsOriginal thenEqualResult`() {
        val list = listOf(Any(), Any())

        assertEquals(list, list.safeSublist(0, list.size))
    }

    @Test fun `givenNonEmptyList whenValidSublistOfSingleItem thenSingleItemResult`() {
        val list = listOf(Any(), Any())
        val from = 0
        val to = from + 1

        assertEquals(1, list.safeSublist(from, to)?.size)
    }

    @Test fun `givenNonEmptyList whenToIndexGreaterThanSize thenSafestSizeResult`() {
        val list = listOf(Any(), Any())
        val from = 0
        val to = 100

        assertEquals(2, list.safeSublist(from, to)?.size)
    }
}