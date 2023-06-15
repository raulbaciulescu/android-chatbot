package com.university.androidchatbot.feature.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.university.androidchatbot.data.Message
import com.university.androidchatbot.data.MessageType
import com.university.androidchatbot.repository.MessageRepository
import com.university.androidchatbot.utils.DefaultPaginator
import com.university.androidchatbot.utils.Util
import com.university.androidchatbot.utils.updateValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.streams.toList

data class ScreenState(
    val isLoading: Boolean = false,
    val isDeleted: Boolean = false,
    val items: List<Message> = mutableStateListOf(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0,
    val chatTitle: String = ""
)

data class MessageState(
    val isLoading: Boolean = false,
    val createMessage: Int = 0
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var chatId: Int = savedStateHandle["chatId"]!!
    var state: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState())
    var messageState by mutableStateOf(MessageState())

    private val paginator = DefaultPaginator(
        initialKey = state.value.page,
        onLoadUpdated = {
            state.updateValue { crtState -> crtState.copy(isLoading = it) }
        },
        onRequest = { chatId, nextPage ->
            messageRepository.getMessages(chatId, nextPage)
        },
        getNextKey = {
            state.value.page + 1
        },
        onError = {
            state.updateValue { crtState -> crtState.copy(error = it?.localizedMessage) }
        },
        onSuccess = { items, newKey ->
            state.updateValue { crtState ->
                crtState.copy(
                    items = (state.value.items + items).distinctBy { it.text },
                    page = newKey,
                    endReached = items.isEmpty()
                )
            }
        }
    )

    init {
        println("init chat")
        loadChat()
    }

    fun loadMoreMessages() {
        println("load more messages: " + chatId)
        if (chatId != 0) {
            viewModelScope.launch {
                state.updateValue { it.copy(isLoading = true) }
                paginator.loadNextItems(chatId)
                state.updateValue { it.copy(isLoading = false) }
            }
        }
    }

    fun sendMessage(text: String) {
        val message = Message(text = text, type = MessageType.USER, chatId = chatId)
        var receivedMessage: Message
        state.updateValue { crtState ->
            crtState.copy(
                items = listOf(message) + state.value.items,
            )
        }
        messageState = messageState.copy(isLoading = true, createMessage = messageState.createMessage + 1)
        viewModelScope.launch {
            receivedMessage = messageRepository.sendMessage(message)
            chatId = receivedMessage.chatId
            messageState = messageState.copy(isLoading = false, createMessage = messageState.createMessage + 1)

            state.updateValue {
                it.copy(
                    items = listOf(receivedMessage) + state.value.items.stream()
                        .map { item -> item.copy(chatId = chatId) }.toList(),
                )
            }
        }
    }

    fun onSendPdfMessage(text: String, path: String) {
        val message = Message(text = text, type = MessageType.USER, chatId = chatId)
        var receivedMessage: Message
        state.updateValue {
            it.copy(
                items = listOf(message) + state.value.items,
            )
        }
        messageState = messageState.copy(isLoading = true, createMessage = messageState.createMessage + 1)
        viewModelScope.launch {
            receivedMessage = messageRepository.sendMessageWithPdf(message, path)
            chatId = receivedMessage.chatId
            messageState = messageState.copy(isLoading = false, createMessage = messageState.createMessage + 1)
            state.updateValue {
                it.copy(
                    items = listOf(receivedMessage) + state.value.items.stream()
                        .map { item -> item.copy(chatId = chatId) }.toList(),
                )
            }
        }
    }

    fun deleteChat() {
        viewModelScope.launch {
            messageRepository.deleteChat(Util.chatId)
            state.updateValue { it.copy(isDeleted = true) }
        }
    }

    fun updateChat(chatTitle: String) {
        viewModelScope.launch {
            state.updateValue { it.copy(chatTitle = chatTitle) }
            messageRepository.updateChat(chatTitle)
        }
    }

    private fun loadChat() {
        println("load chat: " + chatId)
        viewModelScope.launch {
            val newChatTitle =  messageRepository.getChats().firstOrNull { it.id == chatId }?.title ?: ""
            state.updateValue {
                it.copy(
                    chatTitle = newChatTitle
                )
            }
        }
    }
}