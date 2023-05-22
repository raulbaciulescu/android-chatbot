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

sealed interface MessageUiState {
    data class Success(val items: List<Message>) : MessageUiState
    data class Error(val exception: Throwable?) : MessageUiState
    object Loading : MessageUiState
}

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
    private var _messages = mutableStateListOf<Message>()
    val messages: List<Message>
        get() = _messages.toList()

    var uiState: MessageUiState by mutableStateOf(MessageUiState.Loading)
        private set

    fun getMessagesByChat(chatId: Int) {
//        viewModelScope.launch {
//            uiState = MessageUiState.Loading
//            var result: List<Message> = listOf()
//            if (chatId != 0)
//                result = messageRepository.getMessages(chatId)
//            _messages = result.toMutableStateList()
//            uiState = MessageUiState.Success(result)
//        }
        viewModelScope.launch {
            paginator.loadNextItems(chatId)
        }
    }

    fun addMessage(text: String, chatId: Int) {
        val message = Message(text = text, type = MessageType.USER, chatId = chatId)
        var receivedMessage: Message
        _messages.add(message)
        uiState = MessageUiState.Success(_messages)
        state = state.copy(
            items = listOf(message) + state.items,
        )
        println("^^^^^^ " + state.items)

        //uiState = MessageUiState.Loading
        viewModelScope.launch {
            receivedMessage = messageRepository.sendMessage(message)
            _messages.add(receivedMessage)
            uiState = MessageUiState.Success(_messages)
            state = state.copy(
                items = listOf(receivedMessage) + state.items,
            )
            println("^^^^^^ " + state.items)
        }
    }

    var state by mutableStateOf(ScreenState())
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