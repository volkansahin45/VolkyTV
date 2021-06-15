package com.vsahin.volkytv.ui.home

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
class HomeViewModel @Inject constructor(
    private val repository: EntryRepository
) : ViewModel() {

    private var _homeState = mutableStateOf(HomeState())
    val homeState: State<HomeState> = _homeState

    init {
        getContents()
    }

    fun getContents(){
        _homeState.value = _homeState.value.copy(isLoading = true)
        viewModelScope.launch {
            _homeState.value = when (val result = repository.getEntries()) {
                is Result.Success -> {
                    _homeState.value.copy(isLoading = false, contents = result.data)
                }
                is Result.Error -> {
                    _homeState.value.copy(isLoading = false, error = result.exception)
                }
            }
        }
    }
}
