package com.university.androidchatbot.screen


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.university.androidchatbot.viewmodel.MessageViewModel

@Composable
fun ChatScreen(chatId: Int) {
    val messageViewModel = hiltViewModel<MessageViewModel>()

    Column {
        ChatSection(Modifier.weight(1f))
        MessageSection(
            onSendMessage = { text ->
                messageViewModel.addMessage(text, chatId)
            },
            audioRecorder = messageViewModel.audioRecorder
        )
    }

    LaunchedEffect(true) {
        messageViewModel.getMessagesByChat(chatId)
    }
}