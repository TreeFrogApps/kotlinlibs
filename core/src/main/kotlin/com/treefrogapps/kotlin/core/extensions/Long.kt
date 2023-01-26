package com.treefrogapps.kotlin.core.extensions

import java.util.concurrent.TimeUnit

enum class DurationFormat(val format: String) {
    Seconds(format = "%d"),
    MinutesSeconds(format = "%d:%02d"),
    HoursMinutesSeconds(format = "%d:%02d:%02d"),
}

fun Long.formattedDuration(
    timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
    format: DurationFormat = DurationFormat.MinutesSeconds
): String {
    return when (format) {
        DurationFormat.Seconds             ->
            String.format(format.format, timeUnit.toSeconds(this))

        DurationFormat.MinutesSeconds      ->
            String.format(
                format.format,
                timeUnit.toMinutes(this),
                timeUnit.toSeconds(this) % 60)

        DurationFormat.HoursMinutesSeconds ->
            String.format(
                format.format,
                timeUnit.toHours(this),
                timeUnit.toMinutes(this) % 60,
                timeUnit.toSeconds(this) % 60)
    }
}

