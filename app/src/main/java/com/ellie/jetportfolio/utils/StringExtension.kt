package com.ellie.jetportfolio.utils

import java.text.DecimalFormat
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

fun String.toYearMonth(inFormat: String): YearMonth = YearMonth.parse(
    this, DateTimeFormatter.ofPattern(inFormat, Locale.ENGLISH)
)

fun YearMonth.toString(outFormat: String): String =
    DateTimeFormatter.ofPattern(outFormat, Locale.ENGLISH).format(this)

fun Double.toString(outFormat: String): String = DecimalFormat(outFormat).format(this)