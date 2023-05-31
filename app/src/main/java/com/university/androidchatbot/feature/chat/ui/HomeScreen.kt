package com.university.androidchatbot.feature.chat.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.university.androidchatbot.HOME_ROUTE
import com.university.androidchatbot.components.DrawerScreen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    chatId: Int,
    navigate: (chatId: Int) -> Unit,
    onNewChatClick: () -> Unit,
    onNewChatWithPdfClick: (String) -> Unit,
    onLogoutClick: () -> Unit,
    chatViewModel: ChatViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.fillMaxWidth(.8f)) {
                DrawerScreen(
                    chatViewModel = chatViewModel,
                    onNewChatClick = onNewChatClick,
                    navigate = navigate,
                    onNewChatWithPdfClick = { path ->
                        onNewChatWithPdfClick(path)
                    },
                    onLogoutClick = onLogoutClick,
                    application = chatViewModel.application
                )
            }
        },
        content = {
            ChatScreen(
                chatTitle = chatViewModel.chatUiState.items.find { it.id == chatId }?.title,
                chatId = chatId,
                open = {
                    scope.launch {
                        drawerState.open()
                    }
                },
                onDeleteChat = {
                    chatViewModel.deleteChat()
                    navigate(0)
                }
            )
        }
    )

    LaunchedEffect(drawerState.currentValue) {
        if (drawerState.currentValue == DrawerValue.Open)
            chatViewModel.refreshChats()
    }
}