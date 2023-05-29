package com.university.androidchatbot.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.university.androidchatbot.viewmodel.ChatViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    chatId: Int,
    navigate: (chatId: Int) -> Unit,
    onNewChatClick: () -> Unit,
    onNewChatWithPdfClick: (String) -> Unit,
    chatViewModel: ChatViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.fillMaxWidth(.7f)) {
                DrawerScreen(
                    chatViewModel = chatViewModel,
                    onNewChatClick = onNewChatClick,
                    navigate = navigate,
                    onNewChatWithPdfClick = { path ->
                        onNewChatWithPdfClick(path)
                    },
                    application = chatViewModel.application
                )
            }
        },
        content = {
            ChatScreen(
                chatTitle = chatViewModel.chats.find { it.id == chatId }?.title,
                chatId = chatId,
                open = {
                    scope.launch {
                        chatViewModel.refreshChats()
                        drawerState.open()
                    }
                }
            )
        }
    )
}