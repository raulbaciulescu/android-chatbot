package com.university.androidchatbot.todo

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.university.androidchatbot.core.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepository: MessageRepository
) : ViewModel() {
    private var _messages = mutableStateListOf<Message>()
    val messages: List<Message>
        get() = _messages.toList()

    init {
        viewModelScope.launch {
            println("xx: before")
            val result = messageRepository.getMessages(4)
            _messages = result.toMutableStateList()

            println("xxd: " + _messages.toList())
            println(_messages)
            println(_messages.toList())
        }
    }

    fun getMessagesByChat(chatId: Int) {
        viewModelScope.launch {
            var result = messageRepository.getMessages(1)
        }
    }

    fun addMessage(text: String) {
        _messages.add(Message(text = text, type = MessageType.USER))
    }
}