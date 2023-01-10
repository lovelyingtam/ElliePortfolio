package com.ellie.jetportfolio.utils

import android.content.Context
import com.ellie.jetportfolio.R
import java.time.YearMonth
import java.time.temporal.ChronoUnit

internal object StringFormatter {

    // Profile display formatter
    const val YMInputFormat: String = "yyyy-MM-dd"
    const val YMOutFormat: String = "MMM yyyy"
    const val DecimalOutFormat: String = "0.#"

    fun formatYMDuration(
        context: Context,
        startYM: YearMonth,
        endYM: YearMonth?,
    ): String {
        var ymDuration = calculateYearMonthDuration(
            context = context,
            startYM = startYM,
            endYM = endYM ?: YearMonth.now(),
        )
        if (ymDuration.isNotEmpty()) {
            ymDuration = "($ymDuration)"
        }
        val endYMStr =
            endYM?.let { endYM.toString(YMOutFormat) } ?: context.getString(R.string.present)
        return "${startYM.toString(YMOutFormat)} - $endYMStr $ymDuration"
    }

    fun calculateYearMonthDuration(
        context: Context,
        startYM: YearMonth,
        endYM: YearMonth,
    ): String {
        val durationInMonth: Long = startYM.until(endYM, ChronoUnit.MONTHS)
        val diffYear: Int = (durationInMonth / 12).toInt()
        val diffMonth: Int = (durationInMonth % 12).toInt()
        var result = ""

        if (diffYear > 0) {
            result = "$diffYear ${
                context.resources.getQuantityString(
                    R.plurals.yr, diffYear,
                )
            }"
        }
        if (diffMonth > 0) {
            if (result.isNotEmpty()) {
                result += " "
            }
            result += "$diffMonth ${
                context.resources.getQuantityString(
                    R.plurals.mo, diffMonth,
                )
            }"
        }
        return result
    }
}