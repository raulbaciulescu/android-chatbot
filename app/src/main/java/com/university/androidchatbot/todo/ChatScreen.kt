package com.university.androidchatbot.todo

//import androidx.compose.foundation.LocalWindowInsets
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.university.androidchatbot.core.ui.UserPreferencesViewModel

@Composable
fun ChatScreen(chatId: Int) {
    val chatViewModel = hiltViewModel<ChatViewModel>()

    Column() {
        ChatSection(Modifier.weight(1f), chatViewModel.messages.toList())
        MessageSection(onSendMessage = { text ->
            chatViewModel.addMessage(text)
        })
    }
}