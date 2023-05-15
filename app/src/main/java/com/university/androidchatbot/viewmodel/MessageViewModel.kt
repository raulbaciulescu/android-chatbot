package com.university.androidchatbot.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.university.androidchatbot.data.Message
import com.university.androidchatbot.data.MessageType
import com.university.androidchatbot.todo.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MessageUiState {
    data class Success(val items: List<Message>) : MessageUiState
    data class Error(val exception: Throwable?) : MessageUiState
    object Loading : MessageUiState
}

//data class MessageUiState(
//    val isLoading: Boolean = false,
//    val loadingError: Throwable? = null,
//    val itemId: String? = null,
//    val item: Message? = null,
//    val isSaving: Boolean = false,
//    val savingCompleted: Boolean = false,
//    val savingError: Throwable? = null,
//)


@HiltViewModel
class MessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository
) : ViewModel() {
    private var _messages = mutableStateListOf<Message>()
    val messages: List<Message>
        get() = _messages.toList()

    var uiState: MessageUiState by mutableStateOf(MessageUiState.Loading)
        private set
//    var messageUiState: MessageUiState by mutableStateOf(MessageUiState(isLoading = true))
//        private set




    init {
//        viewModelScope.launch {
//            uiState = MessageUiState.Loading
//            val result = messageRepository.getMessages(4)
//            _messages = result.toMutableStateList()
//            uiState = MessageUiState.Success(result)
//        }
    }

    fun getMessagesByChat(chatId: Int) {
        viewModelScope.launch {
            uiState = MessageUiState.Loading
            val result = messageRepository.getMessages(chatId)
            _messages = result.toMutableStateList()
            uiState = MessageUiState.Success(result)
        }
    }

    fun addMessage(text: String) {
        val message = Message(text = text, type = MessageType.USER)
        var receivedMessage: Message
        _messages.add(message)
        uiState = MessageUiState.Success(_messages)
        viewModelScope.launch {
            receivedMessage = messageRepository.sendMessage(message)
            _messages.add(receivedMessage)
        }
    }
}