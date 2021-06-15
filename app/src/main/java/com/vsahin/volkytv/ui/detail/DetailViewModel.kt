package com.vsahin.volkytv.ui.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsahin.volkytv.data.EntryRepository
import com.vsahin.volkytv.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: EntryRepository
) : ViewModel() {
    private val _detailState: MutableState<DetailState> = mutableStateOf(DetailState())
    val detailState: State<DetailState> = _detailState

    fun getDetail(entryId: String) {
        _detailState.value = _detailState.value.copy(isLoading = true)

        viewModelScope.launch {
            _detailState.value = when (val result = repository.getEntry(entryId)) {
                is Result.Success -> {
                    _detailState.value.copy(isLoading = false, entry = result.data)
                }
                is Result.Error -> {
                    _detailState.value.copy(isLoading = false, error = result.exception)
                }
            }
        }
    }
}