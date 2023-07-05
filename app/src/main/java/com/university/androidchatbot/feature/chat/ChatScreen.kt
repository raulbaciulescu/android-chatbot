package com.university.androidchatbot.feature.chat

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.university.androidchatbot.ui.components.AppBar
import com.university.androidchatbot.ui.components.MessageSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    onMenuClick: () -> Unit,
    onChatDeleted: () -> Unit,
    onNewPdfChatClick: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val messageState = viewModel.messageState

    LaunchedEffect(state.isDeleted) {
        if (state.isDeleted) {
            onChatDeleted()
        }
    }

    LaunchedEffect(true) {
        viewModel.loadMoreMessages()
    }

    Scaffold(
        topBar = {
            AppBar(
                chatTitle = state.chatTitle,
                onMenuClick = onMenuClick,
                onDeleteClick = {
                    viewModel.deleteChat()
                },
                onUpdateClick = { chatTitle ->
                    viewModel.updateChat(chatTitle)
                },
                onNewPdfChatClick = onNewPdfChatClick,
            )
        },
        bottomBar = {
            MessageSection(
                messageState = viewModel.messageState,
                onSendMessage = { text ->
                    viewModel.sendMessage(text)
                },
                onSendPdfMessage = { text, path ->
                    viewModel.onSendPdfMessage(text, path)
                }
            )
        }
    ) {
        MessagesSection(
            modifier = Modifier.padding(
                bottom = it.calculateBottomPadding(),
                top = it.calculateTopPadding()
            ),
            state = state,
            messageState = messageState,
            loadMoreMessages = viewModel::loadMoreMessages,
            clearError = viewModel::clearError
        )
    }
}