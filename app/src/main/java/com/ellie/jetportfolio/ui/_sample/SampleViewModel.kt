package com.ellie.jetportfolio.ui._sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SampleUiState())
    val uiState: StateFlow<SampleUiState>
        get() = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        _uiState.value = SampleUiState()

        viewModelScope.launch {
            delay(2000)
        }
    }

    fun add() {
        _uiState.update {
            it.copy(
                count = _uiState.value.count + 1,
                message = "Added: ${_uiState.value.count}",
            )
        }
    }
}