package com.university.androidchatbot.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.squti.androidwaverecorder.WaveRecorder
import com.university.androidchatbot.api.SpeechRecognitionDataSource
import com.university.androidchatbot.data.Message
import com.university.androidchatbot.data.MessageType
import com.university.androidchatbot.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

sealed interface MessageUiState {
    data class Success(val items: List<Message>) : MessageUiState
    data class Error(val exception: Throwable?) : MessageUiState
    object Loading : MessageUiState
}

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
) : ViewModel() {
    private var _messages = mutableStateListOf<Message>()
    val messages: List<Message>
        get() = _messages.toList()

    var uiState: MessageUiState by mutableStateOf(MessageUiState.Loading)
        private set

    fun getMessagesByChat(chatId: Int) {
        viewModelScope.launch {
            uiState = MessageUiState.Loading
            var result: List<Message> = listOf()
            if (chatId != 0)
                result = messageRepository.getMessages(chatId)
            _messages = result.toMutableStateList()
            uiState = MessageUiState.Success(result)
        }
    }

    fun addMessage(text: String, chatId: Int) {
        val message = Message(text = text, type = MessageType.USER, chatId = chatId)
        var receivedMessage: Message
        _messages.add(message)
        uiState = MessageUiState.Success(_messages)
        //uiState = MessageUiState.Loading
        viewModelScope.launch {
            receivedMessage = messageRepository.sendMessage(message)
            _messages.add(receivedMessage)
            uiState = MessageUiState.Success(_messages)
        }
    }
}