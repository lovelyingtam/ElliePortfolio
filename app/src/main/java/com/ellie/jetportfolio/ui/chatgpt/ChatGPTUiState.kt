package com.ellie.jetportfolio.ui.chatgpt

import java.util.*

data class ChatGPTUiState(
    val messages: LinkedList<String> = LinkedList<String>(),
)
