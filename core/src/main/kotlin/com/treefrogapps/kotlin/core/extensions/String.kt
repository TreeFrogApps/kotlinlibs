package com.treefrogapps.kotlin.core.extensions


fun emptyString() : String = ""

fun firstCharIfDifferent(
    string: String,
    string2: String
): Char? =
    string2.firstOrNull()
        ?.uppercaseChar()
        .let { char -> if (char != string.firstOrNull()?.uppercaseChar()) char else null }
