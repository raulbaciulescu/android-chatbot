package com.university.androidchatbot.screen


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.university.androidchatbot.viewmodel.MessageViewModel

@Composable
fun ChatScreen(
    chatId: Int,
    messageViewModel: MessageViewModel = hiltViewModel(),
    open: () -> Unit
) {
    Scaffold(
        topBar = {
            AppBar(
                onNavigationIconClick = {
                    open()
                }
            )
        },
        bottomBar = {
            MessageSection(
                onSendMessage = { text ->
                    messageViewModel.addMessage(text, chatId)
                }
            )
        }
    ) {
        ChatSection(
            modifier = Modifier.padding(
                bottom = it.calculateBottomPadding(),
                top = it.calculateTopPadding()
            )
        )
    }

    LaunchedEffect(true) {
        messageViewModel.getMessagesByChat(chatId)
    }
}