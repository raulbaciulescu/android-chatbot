package com.university.androidchatbot.feature.chat.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.university.androidchatbot.data.Chat
import com.university.androidchatbot.feature.chat.api.MessageRepository
import com.university.androidchatbot.utils.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ChatUiState(
    val isLoading: Boolean = false,
    val items: List<Chat> = mutableStateListOf(),
    val error: String? = null,
)


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    val application: Application
) : ViewModel() {
    private var _chatUiState by mutableStateOf(ChatUiState())
    val chatUiState: ChatUiState
        get() = _chatUiState

    init {
        refreshChats()
    }

    fun refreshChats() {
        _chatUiState = _chatUiState.copy(isLoading = true)
        viewModelScope.launch {
            val result = messageRepository.getChats()
            _chatUiState = _chatUiState.copy(isLoading = false, items = result)
        }
    }

    fun deleteChat() {
        viewModelScope.launch {
            _chatUiState = _chatUiState.copy(isLoading = false, items = _chatUiState.items.toMutableList().apply {
                removeIf { c -> c.id == Util.chatId }
            })
            messageRepository.deleteChat(Util.chatId)
        }
    }
}