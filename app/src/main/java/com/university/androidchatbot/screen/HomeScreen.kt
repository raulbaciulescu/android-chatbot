package com.university.androidchatbot.screen

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.hilt.navigation.compose.hiltViewModel
import com.university.androidchatbot.viewmodel.ChatViewModel
import com.university.androidchatbot.viewmodel.MessageViewModel

@Composable
fun HomeScreen(
    chatId: Int,
    navigate: (chatId: Int) -> Unit,
    onNewChatClick: () -> Unit,
    chatViewModel: ChatViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

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