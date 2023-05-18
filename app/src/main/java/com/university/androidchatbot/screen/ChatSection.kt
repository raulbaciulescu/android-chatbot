package com.university.androidchatbot.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.university.androidchatbot.data.MessageType
import com.university.androidchatbot.viewmodel.MessageUiState
import com.university.androidchatbot.viewmodel.MessageViewModel
import kotlin.math.min

@Composable
fun ChatSection(
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val messageViewModel = hiltViewModel<MessageViewModel>()

    when (val uiState = messageViewModel.uiState) {
        is MessageUiState.Success ->
            LazyColumn(
                state = listState,
                modifier = modifier
                    .padding(16.dp)
            ) {
                items(uiState.items) { message ->
                    MessageItem(
                        messageText = message.text,
                        type = message.type
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        is MessageUiState.Loading -> LoadingAnimation()
        is MessageUiState.Error -> Text(text = "Failed to load items - ${uiState.exception?.message}")
    }


    LaunchedEffect(messageViewModel.messages) {
        listState.scrollToItem(if (messageViewModel.messages.isNotEmpty()) messageViewModel.messages.size - 1 else 0)
    }
}

private val BotChatBubbleShape = RoundedCornerShape(0.dp, 20.dp, 20.dp, 20.dp)
private val AuthorChatBubbleShape = RoundedCornerShape(20.dp, 0.dp, 20.dp, 20.dp)

@Composable
fun MessageItem(messageText: String, type: MessageType) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (type == MessageType.USER) MaterialTheme.colors.primary else Color(0xFF616161),
                    shape = if (type == MessageType.USER) AuthorChatBubbleShape else BotChatBubbleShape
                )
                .padding(
                    top = 8.dp,
                    bottom = 8.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .widthIn(min = 50.dp, max = 250.dp)
                .align(if (type == MessageType.USER) Alignment.End else Alignment.Start)
        ) {
            Text(
                text = messageText,
                color = Color.White
            )
        }
    }
}