package com.university.androidchatbot.feature.chat.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.university.androidchatbot.data.Chat
import com.university.androidchatbot.feature.chat.api.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    val application: Application
) : ViewModel() {
    private var _chats = mutableStateListOf<Chat>()
    val chats: List<Chat>
        get() = _chats.toList()

    init {
        println("ChatViewModel init")
        viewModelScope.launch {
            val result = messageRepository.getChats()
            _chats = result.toMutableStateList()
        }
    }

    fun refreshChats() {
        println("ChatViewModel refresh")
        viewModelScope.launch {
            val result = messageRepository.getChats()
            _chats = result.toMutableStateList()
        }
    }
}