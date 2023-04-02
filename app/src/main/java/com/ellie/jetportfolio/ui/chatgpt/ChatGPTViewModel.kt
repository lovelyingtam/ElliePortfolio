package com.ellie.jetportfolio.ui.chatgpt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ellie.jetportfolio.utils.addToQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatGPTViewModel @Inject constructor() : ViewModel() {

    private val MAX_SIZE: Int = 10
    private val _uiState = MutableStateFlow(ChatGPTUiState())
    val uiState: StateFlow<ChatGPTUiState>
        get() = _uiState.asStateFlow()

    init {
        newChat()
    }

    fun newChat() {
        _uiState.value = ChatGPTUiState()

        viewModelScope.launch {
            delay(2000)
        }
    }

    fun send(message: String) {
        _uiState.update { currentState ->
            val updatedState = currentState.copy(
                messages = currentState.messages.addToQueue(message, MAX_SIZE)
            )
            updatedState
        }
    }
}