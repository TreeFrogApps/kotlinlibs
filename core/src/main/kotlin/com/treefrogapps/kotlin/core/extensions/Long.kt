package com.treefrogapps.kotlin.core.extensions

import com.treefrogapps.kotlin.core.extensions.SizeUnit.Companion.toBytes
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
            String.format(
                format.format,
                timeUnit.toSeconds(this))

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

enum class SizeUnit {
    Bytes,
    KiloBytes,
    MegaBytes,
    GigaBytes;

    companion object {
        private const val SIScale: Long = 1000L

        fun SizeUnit.toBytes(size: Long): Long =
            when (this) {
                Bytes     -> size
                KiloBytes -> size * SIScale
                MegaBytes -> size * SIScale * SIScale
                GigaBytes -> size * SIScale * SIScale * SIScale
            }
    }
}

enum class SizeFormat(val format: String) {
    B(format = "%,db"),
    Kb(format = "%,dKb"),
    Mb(format = "%,.2fMb"),
    Gb(format = "%,.2fGb"),
}

/**
 * Use SI format (not Decimal) :
 * 1,000 bytes == 1 Kilobyte
 * 1,000 Kilobytes == 1 Megabyte
 * 1,000 Megabytes = 1 Gigabyte
 */
fun Long.formattedSize(
    sizeUnit: SizeUnit = SizeUnit.Bytes,
    format: SizeFormat = SizeFormat.Mb
): String {
    return when (format) {
        SizeFormat.B  ->
            String.format(
                format.format,
                sizeUnit.toBytes(size = this))

        SizeFormat.Kb ->
            String.format(
                format.format,
                sizeUnit.toBytes(size = this) / 1_000)

        SizeFormat.Mb ->
            String.format(
                format.format,
                sizeUnit.toBytes(size = this).toDouble() / 1_000_000)

        SizeFormat.Gb ->
            String.format(
                format.format,
                sizeUnit.toBytes(size = this).toDouble() / 1_000_000_000)

    }
}

