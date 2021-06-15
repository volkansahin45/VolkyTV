package com.vsahin.volkytv.ui.detail

import com.vsahin.volkytv.data.model.Entry

data class DetailState(
    val entry: Entry? = null,
    val isLoading: Boolean = false,
    val error: Exception? = null
)