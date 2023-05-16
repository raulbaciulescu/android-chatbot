package com.university.androidchatbot.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.hilt.navigation.compose.hiltViewModel
import com.university.androidchatbot.data.Chat
import com.university.androidchatbot.todo.AudioRecorderImpl
import com.university.androidchatbot.viewmodel.ChatViewModel

@Composable
fun HomeScreen(
    chatId: Int,
    navigate: (chatId: Int) -> Unit,
    onNewChatClick: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val chatViewModel = hiltViewModel<ChatViewModel>()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                onNavigationIconClick = {
                    scope.launch {
                        chatViewModel.refreshChats()
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            DrawerHeader(onNewChatClick = onNewChatClick)
            DrawerBody(
                items = chatViewModel.chats,
                onItemClick = {
                    navigate(it.id)
                }
            )
        }
    ) {
        ChatScreen(chatId)
    }
}