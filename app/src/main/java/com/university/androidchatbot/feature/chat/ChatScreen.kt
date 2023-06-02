//package com.university.androidchatbot.feature.chat
//
//
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Scaffold
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Modifier
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.university.androidchatbot.ui.components.AppBar
//import com.university.androidchatbot.ui.components.MessageSection
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ChatScreen(
//    chatTitle: String? = "",
//    chatId: Int,
//    messageViewModel: MessageViewModel = hiltViewModel(),
//    open: () -> Unit,
//    onDeleteChat: () -> Unit,
//    onUpdateClick: (String) -> Unit
//) {
//    Scaffold(
//        topBar = {
//            AppBar(
//                chatTitle = chatTitle ?: "",
//                onMenuClick = {
//                    open()
//                },
//                onDeleteClick = onDeleteChat,
//                onUpdateClick = onUpdateClick
//            )
//        },
//        bottomBar = {
//            MessageSection(
//                onSendMessage = { text ->
//                    messageViewModel.sendMessage(text, chatId)
//                },
//                onSendPdfMessage = { text, path ->
//                    messageViewModel.onSendPdfMessage(text, path, chatId)
//                }
//            )
//        }
//    ) {
//        MessagesSection(
//            modifier = Modifier.padding(
//                bottom = it.calculateBottomPadding(),
//                top = it.calculateTopPadding()
//            )
//        )
//    }
//
//    LaunchedEffect(true) {
//        messageViewModel.getMessagesByChat(chatId)
//    }
//}