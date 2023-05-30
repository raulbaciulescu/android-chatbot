package com.university.androidchatbot.feature.chat.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.university.androidchatbot.data.Message
import com.university.androidchatbot.data.MessageType
import com.university.androidchatbot.feature.chat.api.MessageRepository
import com.university.androidchatbot.utils.DefaultPaginator
import com.university.androidchatbot.utils.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.streams.toList


data class ScreenState(
    val isLoading: Boolean = false,
    val items: List<Message> = mutableStateListOf(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)

data class MessageState(
    val isLoading: Boolean = false,
    val createMessage: Int = 0
)

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
) : ViewModel() {
    var state by mutableStateOf(ScreenState())
    var messageState by mutableStateOf(MessageState())

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

    fun getMessagesByChat(chatId: Int) {
        viewModelScope.launch {
            paginator.loadNextItems(chatId)
        }
    }

    fun sendMessage(text: String, chatId: Int) {
        val message = Message(text = text, type = MessageType.USER, chatId = Util.chatId)
        var receivedMessage: Message
        state = state.copy(
            items = listOf(message) + state.items,
        )
        messageState = messageState.copy(isLoading = true, createMessage = messageState.createMessage + 1)
        viewModelScope.launch {
            receivedMessage = messageRepository.sendMessage(message)
            Util.chatId = receivedMessage.chatId
            messageState = messageState.copy(isLoading = false, createMessage = messageState.createMessage + 1)
            state = state.copy(
                items = listOf(receivedMessage) + state.items.stream().map { item -> item.copy(chatId = Util.chatId) }.toList(),
            )
        }
    }

    fun onSendPdfMessage(text: String, path: String, chatId: Int) {
        val message = Message(text = text, type = MessageType.USER, chatId = Util.chatId)
        var receivedMessage: Message
        state = state.copy(
            items = listOf(message) + state.items,
        )
        viewModelScope.launch {
            receivedMessage = messageRepository.sendMessageWithPdf(message, path)
            Util.chatId = receivedMessage.chatId
            state = state.copy(
                items = listOf(receivedMessage) + state.items.stream().map { item -> item.copy(chatId = Util.chatId) }.toList(),
            )
        }
    }
}