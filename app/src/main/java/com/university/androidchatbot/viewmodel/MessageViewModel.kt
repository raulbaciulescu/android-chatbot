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
import com.university.androidchatbot.repository.MessageRepository
import com.university.androidchatbot.todo.v1.DefaultPaginator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ScreenState(
    val isLoading: Boolean = false,
    val items: List<Message> = mutableStateListOf(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
) : ViewModel() {
    var state by mutableStateOf(ScreenState())

    fun getMessagesByChat(chatId: Int) {
        viewModelScope.launch {
            paginator.loadNextItems(chatId)
        }
    }

    fun addMessage(text: String, chatId: Int) {
        val message = Message(text = text, type = MessageType.USER, chatId = chatId)
        var receivedMessage: Message
        state = state.copy(
            items = listOf(message) + state.items,
        )
        viewModelScope.launch {
            receivedMessage = messageRepository.sendMessage(message)
            state = state.copy(
                items = listOf(receivedMessage) + state.items,
            )
        }
    }

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { chatId, nextPage ->
            messageRepository.getMessages(chatId, nextPage)
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                items = state.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    fun loadNextItems(chatId: Int) {
        viewModelScope.launch {
            paginator.loadNextItems(chatId)
        }
    }
}