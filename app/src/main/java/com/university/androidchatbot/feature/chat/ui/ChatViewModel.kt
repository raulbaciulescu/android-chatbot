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
import com.university.androidchatbot.utils.updateValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ChatUiState(
    val isLoading: Boolean = false,
    val items: List<Chat> = mutableStateListOf(),
    val error: String? = null,
    var currentChat: Chat? = null
)


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    val application: Application
) : ViewModel() {
    private val _chatUiState: MutableStateFlow<ChatUiState> = MutableStateFlow(ChatUiState())
    val chatUiState: StateFlow<ChatUiState>
        get() = _chatUiState.asStateFlow()

    fun refreshChats() {
        _chatUiState.updateValue { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = messageRepository.getChats()
            _chatUiState.updateValue { it.copy(isLoading = false, items = result) }
        }
    }

    fun deleteChat() {
        viewModelScope.launch {
            _chatUiState.updateValue {
                it.copy(
                    isLoading = false,
                    items = it.items.toMutableList().apply {
                        removeIf { c -> c.id == Util.chatId }
                    }
                )
            }
            messageRepository.deleteChat(Util.chatId)
        }
    }

    fun updateChat(chatTitle: String) {
        viewModelScope.launch {
           val newChat = _chatUiState.value.items.firstOrNull { it.id == Util.chatId }
            //it.currentChat?.copy(title = chatTitle),
            println("rrrrrrr " + newChat)
            println("rrrrrrr " + _chatUiState.value.currentChat)
            _chatUiState.updateValue {
                it.copy(
                    currentChat = newChat?.copy(title = chatTitle),
                    isLoading = false,
                    items = it.items.toMutableList().apply {
                        map { chat ->
                            if (chat.id == Util.chatId) {
                                chat.title = chatTitle
                            }
                            chat
                        }
                    }
                )
            }
            messageRepository.updateChat(chatTitle)
            refreshChats()
        }
    }

    fun selectChat(chatId: Int) {
        _chatUiState.updateValue {
            it.copy(
                currentChat = it.items.firstOrNull { chat -> chat.id == chatId }
            )
        }
    }
}