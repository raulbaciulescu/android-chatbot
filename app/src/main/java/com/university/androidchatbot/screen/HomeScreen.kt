package com.university.androidchatbot.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.university.androidchatbot.R
import com.university.androidchatbot.todo.v1.PressableButton
import com.university.androidchatbot.viewmodel.ChatViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    chatId: Int,
    navigate: (chatId: Int) -> Unit,
    onNewChatClick: () -> Unit,
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
                    navigate = navigate
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