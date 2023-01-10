package com.ellie.jetportfolio

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ellie.jetportfolio.utils.StringFormatter
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.time.YearMonth

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class StringFormatterTest {


    // ----- calculateYearMonthDuration -----

    @Test
    fun calculateYearMonthDuration_withYMObject_1yr1mo() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val result = StringFormatter.calculateYearMonthDuration(
            context = context,
            startYM = YearMonth.parse("1900-01"),
            endYM = YearMonth.parse("1901-02"),
        )
        assertEquals("1 yr 1 mo", result)
    }

    @Test
    fun calculateYearMonthDuration_withYMObject_NyrNmos() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val result = StringFormatter.calculateYearMonthDuration(
            context = context,
            startYM = YearMonth.parse("1900-01"),
            endYM = YearMonth.parse("1910-10"),
        )
        assertEquals("10 yrs 9 mos", result)
    }

    @Test
    fun calculateYearMonthDuration_withYMObject_0yrNmos() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val result = StringFormatter.calculateYearMonthDuration(
            context = context,
            startYM = YearMonth.parse("1900-01"),
            endYM = YearMonth.parse("1900-10"),
        )
        assertEquals("9 mos", result)
    }

    @Test
    fun calculateYearMonthDuration_withYMObject_Nyr0mos() {
        // Context of the app under test.
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val result = StringFormatter.calculateYearMonthDuration(
            context = context,
            startYM = YearMonth.parse("1900-01"),
            endYM = YearMonth.parse("1910-01"),
        )
        assertEquals("10 yrs", result)
    }

    @Test
    fun calculateYearMonthDuration_withYMObject_Now() {
        // Context of the app under test.
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val result = StringFormatter.calculateYearMonthDuration(
            context = context,
            startYM = YearMonth.parse("2022-01"),
            endYM = YearMonth.now(),
        )
        assertEquals("10 mos", result)
    }

    // ----- formatYMDuration ------

    @Test
    fun formatYMDuration_withYMObject_1yr1mo() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val result = StringFormatter.formatYMDuration(
            context = context,
            startYM = YearMonth.parse("1900-01"),
            endYM = YearMonth.parse("1901-02"),
        )
        assertEquals("Jan 1900 - Feb 1901 (1 yr 1 mo)", result)
    }

    @Test
    fun formatYMDuration_withYMObject_NyrNmos() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val result = StringFormatter.formatYMDuration(
            context = context,
            startYM = YearMonth.parse("1900-01"),
            endYM = YearMonth.parse("1910-10"),
        )
        assertEquals("Jan 1900 - Oct 1910 (10 yrs 9 mos)", result)
    }

    @Test
    fun formatYMDuration_withYMObject_0yrNmos() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val result = StringFormatter.formatYMDuration(
            context = context,
            startYM = YearMonth.parse("1900-01"),
            endYM = YearMonth.parse("1900-10"),
        )
        assertEquals("Jan 1900 - Oct 1900 (9 mos)", result)
    }

    @Test
    fun formatYMDuration_withYMObject_Nyr0mos() {
        // Context of the app under test.
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val result = StringFormatter.formatYMDuration(
            context = context,
            startYM = YearMonth.parse("1900-01"),
            endYM = YearMonth.parse("1910-01"),
        )
        assertEquals("Jan 1900 - Jan 1910 (10 yrs)", result)
    }

    @Test
    fun formatYMDuration_withYMObject_Now() {
        // Context of the app under test.
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val result = StringFormatter.formatYMDuration(
            context = context,
            startYM = YearMonth.parse("2021-01"),
            endYM = null,
        )
        assertEquals("Jan 2021 - Present (1 yr 10 mos)", result)
    }
}