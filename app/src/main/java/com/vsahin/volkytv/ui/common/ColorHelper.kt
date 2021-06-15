package com.vsahin.volkytv.ui.common

import androidx.compose.ui.graphics.Color
import java.util.*

val Color.Companion.RandomColor: Color
    get() {
        val random = Random()
        return Color(
            red = random.nextInt(256),
            blue = random.nextInt(256),
            green = random.nextInt(256)
        )
    }