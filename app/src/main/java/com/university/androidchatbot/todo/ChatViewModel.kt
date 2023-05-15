package com.university.androidchatbot.todo

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.university.androidchatbot.core.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ChatUiState {
    data class Success(val items: List<Message>) : ChatUiState
    data class Error(val exception: Throwable?) : ChatUiState
    object Loading : ChatUiState
}

data class MessageUiState(
    val isLoading: Boolean = false,
    val loadingError: Throwable? = null,
    val itemId: String? = null,
    val item: Message? = null,
    val isSaving: Boolean = false,
    val savingCompleted: Boolean = false,
    val savingError: Throwable? = null,
)


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepository: MessageRepository
) : ViewModel() {
    private var _messages = mutableStateListOf<Message>()
    val messages: List<Message>
        get() = _messages.toList()

    var uiState: ChatUiState by mutableStateOf(ChatUiState.Loading)
        private set
    var messageUiState: MessageUiState by mutableStateOf(MessageUiState(isLoading = true))
        private set


    init {
        viewModelScope.launch {
            uiState = ChatUiState.Loading
            val result = messageRepository.getMessages(4)
            _messages = result.toMutableStateList()
            uiState = ChatUiState.Success(result)
        }
    }

    fun getMessagesByChat(chatId: Int) {
        viewModelScope.launch {
            var result = messageRepository.getMessages(1)
        }
    }

    fun addMessage(text: String) {
        _messages.add(Message(text = text, type = MessageType.USER))
        uiState = ChatUiState.Success(_messages)
        _messages.add(Message(text = "response", type = MessageType.AI))
    }
}