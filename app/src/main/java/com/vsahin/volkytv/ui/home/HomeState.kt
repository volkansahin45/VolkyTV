package com.vsahin.volkytv.ui.home

import com.vsahin.volkytv.data.model.Entries

data class HomeState(
    val contents: Entries = Entries(),
    val isLoading: Boolean = false,
    val error: Exception? = null
)