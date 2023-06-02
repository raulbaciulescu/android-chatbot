//package com.university.androidchatbot.feature.home
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.material3.DrawerValue
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.ModalDrawerSheet
//import androidx.compose.material3.ModalNavigationDrawer
//import androidx.compose.material3.rememberDrawerState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import com.university.androidchatbot.feature.chat.ChatScreen
//import com.university.androidchatbot.feature.chat.ChatViewModel
//import com.university.androidchatbot.ui.components.DrawerScreen
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalMaterial3Api::class)
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun HomeScreen(
//    chatId: Int,
//    navigate: (chatId: Int) -> Unit,
//    onNewChatClick: () -> Unit,
//    onNewChatWithPdfClick: (String) -> Unit,
//    onLogoutClick: () -> Unit,
//    chatViewModel: ChatViewModel
//) {
//    val uiState by chatViewModel.chatUiState.collectAsState()
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = {
//            ModalDrawerSheet(modifier = Modifier.fillMaxWidth(.8f)) {
//                DrawerScreen(
//                    chatViewModel = chatViewModel,
//                    onNewChatClick = onNewChatClick,
//                    navigate = navigate,
//                    onNewChatWithPdfClick = { path ->
//                        onNewChatWithPdfClick(path)
//                    },
//                    onLogoutClick = onLogoutClick,
//                    application = chatViewModel.application
//                )
//            }
//        },
//        content = {
//            ChatScreen(
//                chatTitle = uiState.currentChat?.title,
//                chatId = chatId,
//                open = {
//                    scope.launch {
//                        drawerState.open()
//                    }
//                },
//                onDeleteChat = {
//                    chatViewModel.deleteChat()
//                    navigate(0)
//                },
//                onUpdateClick = { chatTitle ->
//                    chatViewModel.updateChat(chatTitle)
//                }
//            )
//        }
//    )
//
//    LaunchedEffect(drawerState.currentValue) {
//        if (drawerState.currentValue == DrawerValue.Open)
//            chatViewModel.refreshChats()
//    }
//}