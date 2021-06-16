package com.vsahin.volkytv.ui.common

import org.junit.Assert
import org.junit.Test

class TimeHelperTest {
    @Test
    fun getReadableTime_zero() {
        val expected = "0s"
        val result = 0L.toReadableTime()

        Assert.assertEquals(expected, result)
    }

    @Test
    fun getReadableTime_only_seconds() {
        val expected = "2s"
        val result = TWO_SECONDS.toReadableTime()

        Assert.assertEquals(expected, result)
    }

    @Test
    fun getReadableTime_only_minutes() {
        val expected = "2m"
        val result = TWO_MINUTES.toReadableTime()

        Assert.assertEquals(expected, result)
    }

    @Test
    fun getReadableTime_only_hours() {
        val expected = "2h"
        val result = TWO_HOURS.toReadableTime()

        Assert.assertEquals(expected, result)
    }

    @Test
    fun getReadableTime_minutes_and_seconds() {
        val expected = "2m 2s"
        val result = TWO_MINUTES_AND_TWO_SECONDS.toReadableTime()

        Assert.assertEquals(expected, result)
    }

    @Test
    fun getReadableTime_hours_and_seconds() {
        val expected = "2h 2s"
        val result = TWO_HOURS_AND_TWO_SECONDS.toReadableTime()

        Assert.assertEquals(expected, result)
    }

    @Test
    fun getReadableTime_hours_and_minutes() {
        val expected = "2h 2m"
        val result = TWO_HOURS_AND_THIRTY_MINUTES.toReadableTime()

        Assert.assertEquals(expected, result)
    }

    companion object {
        const val TWO_SECONDS = 2000L
        const val TWO_MINUTES = 120000L
        const val TWO_HOURS = 7200000L
        const val TWO_MINUTES_AND_TWO_SECONDS = 122000L
        const val TWO_HOURS_AND_TWO_SECONDS = 7202000L
        const val TWO_HOURS_AND_THIRTY_MINUTES = 7320000L
    }
}