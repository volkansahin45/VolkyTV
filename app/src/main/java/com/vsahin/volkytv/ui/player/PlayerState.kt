package com.vsahin.volkytv.ui.player

import com.vsahin.volkytv.data.model.Entry

data class PlayerState(
    val playerPosition: Long = 0L,
    val entry: Entry? = null,
    val isLoading: Boolean = false,
    val error: Exception? = null
)