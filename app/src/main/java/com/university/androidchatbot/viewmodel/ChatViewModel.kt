package com.university.androidchatbot.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.university.androidchatbot.data.Chat
import com.university.androidchatbot.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface ChatUiState {
    data class Success(val items: List<Chat>) : ChatUiState
    data class Error(val exception: Throwable?) : ChatUiState
    object Loading : ChatUiState
}

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    val application: Application
) : ViewModel() {
    fun refreshChats() {
        viewModelScope.launch {
            uiState = ChatUiState.Loading
            val result = messageRepository.getChats()
            _chats = result.toMutableStateList()
            uiState = ChatUiState.Success(result)
        }
    }

    private var _chats = mutableStateListOf<Chat>()
    val chats: List<Chat>
        get() = _chats.toList()

    var uiState: ChatUiState by mutableStateOf(ChatUiState.Loading)
        private set

    init {
        viewModelScope.launch {
            uiState = ChatUiState.Loading
            val result = messageRepository.getChats()
            _chats = result.toMutableStateList()
            uiState = ChatUiState.Success(result)
        }
    }
}