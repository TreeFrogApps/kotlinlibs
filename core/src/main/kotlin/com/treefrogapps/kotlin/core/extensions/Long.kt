package com.treefrogapps.kotlin.core.extensions

import com.treefrogapps.kotlin.core.extensions.SizeUnit.Companion.Scale
import com.treefrogapps.kotlin.core.extensions.SizeUnit.Companion.toBytes
import com.treefrogapps.kotlin.core.extensions.SizeUnit.Companion.toGigaBytes
import com.treefrogapps.kotlin.core.extensions.SizeUnit.Companion.toKiloBytes
import com.treefrogapps.kotlin.core.extensions.SizeUnit.Companion.toMegaBytes
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
        internal const val Scale: Long = 1024L

        fun SizeUnit.toBytes(size: Long): Long =
            when (this) {
                Bytes     -> size
                KiloBytes -> size * Scale
                MegaBytes -> size * Scale * Scale
                GigaBytes -> size * Scale * Scale * Scale
            }

        fun SizeUnit.toKiloBytes(size: Long): Long =
            when (this) {
                Bytes     -> size / Scale
                KiloBytes -> size
                MegaBytes -> size * Scale
                GigaBytes -> size * Scale * Scale
            }

        fun SizeUnit.toMegaBytes(size: Long): Long =
            when (this) {
                Bytes     -> size / (Scale * Scale)
                KiloBytes -> size / Scale
                MegaBytes -> size
                GigaBytes -> size * Scale
            }

        fun SizeUnit.toGigaBytes(size: Long): Long =
            when (this) {
                Bytes     -> size / (Scale * Scale * Scale)
                KiloBytes -> size / (Scale * Scale)
                MegaBytes -> size / Scale
                GigaBytes -> size
            }
    }
}

enum class SizeFormat(val format: String) {
    B(format = "%,db"),
    Kb(format = "%,dKb"),
    Mb(format = "%,d.%02dMb"),
    Gb(format = "%,d.%02dGb"),
}

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
                sizeUnit.toKiloBytes(size = this))

        SizeFormat.Mb ->
            String.format(
                format.format,
                sizeUnit.toMegaBytes(size = this),
                sizeUnit.toKiloBytes(size = this) % Scale)

        SizeFormat.Gb ->
            String.format(
                format.format,
                sizeUnit.toGigaBytes(size = this),
                sizeUnit.toMegaBytes(size = this) % Scale)
    }
}

