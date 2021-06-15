package com.vsahin.volkytv.ui.common

import java.util.concurrent.TimeUnit

fun Long.toReadableDate(): String{
    var ms = this
    val hours = TimeUnit.MILLISECONDS.toHours(ms)
    ms -= TimeUnit.HOURS.toMillis(hours)

    val minutes = TimeUnit.MILLISECONDS.toMinutes(ms)
    ms -= TimeUnit.MINUTES.toMillis(minutes)

    val seconds = TimeUnit.MILLISECONDS.toSeconds(ms);

    var result = ""

    if (hours > 0) {
        result += "${hours}h "
    }

    if (minutes > 0) {
        result += " ${minutes}m"
    }

    if (seconds > 0) {
        result += " ${seconds}s"
    }

    return result
}