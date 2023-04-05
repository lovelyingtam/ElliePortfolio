package com.ellie.jetportfolio.ui._sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ellie.jetportfolio.data.repository.UUIDRepo
import com.ellie.jetportfolio.ui.component.ErrorMessageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val uuidRe: UUIDRepo
) : ViewModel() {

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
            )
        }
    }

    fun fetchUUID() {
        Timber.i("fetchUUID")
        _uiState.update {
            it.copy(
                isRefreshing = true
            )
        }
        viewModelScope.launch {
            try {
                _uiState.value.uuidModel = uuidRe.getUUID()
                Timber.i("_uiState.value.uuidModel ${_uiState.value.uuidModel}")
            } catch (ex: Exception) {
                Timber.e(ex, "refresh() - error")
            }
            _uiState.update {
                it.copy(
                    uuidModel = _uiState.value.uuidModel,
                    isRefreshing = false
                )
            }
        }
    }
}