package com.vsahin.volkytv.ui.player

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
class PlayerViewModel @Inject constructor(
    private val repository: EntryRepository
) : ViewModel() {
    private val _playerState: MutableState<PlayerState> = mutableStateOf(PlayerState())
    val playerState: State<PlayerState> = _playerState

    fun getDetail(entryId: String) {
        _playerState.value = _playerState.value.copy(isLoading = true)

        viewModelScope.launch {
            _playerState.value = when (val result = repository.getEntry(entryId)) {
                is Result.Success -> {
                    _playerState.value.copy(isLoading = false, entry = result.data)
                }
                is Result.Error -> {
                    _playerState.value.copy(isLoading = false, error = result.exception)
                }
            }
        }
    }

    fun updatePlayerPosition(position: Long) {
        _playerState.value = _playerState.value.copy(playerPosition = position)
    }
}