package com.university.androidchatbot.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun ChatSection(
    modifier: Modifier = Modifier,
    messages: List<Message>
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier
            //.fillMaxSize()
            .padding(16.dp),
        //reverseLayout = true
    ) {
        items(messages) { message ->
            MessageItem(
                messageText = message.text,
                type = message.type
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    LaunchedEffect(messages.size) {
        println("dd: " + messages)
        listState.scrollToItem(if (messages.isNotEmpty()) messages.size - 1 else 0)
    }
}

private val BotChatBubbleShape = RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp)
private val AuthorChatBubbleShape = RoundedCornerShape(8.dp, 0.dp, 8.dp, 8.dp)
val message = mutableStateOf("")

@Composable
fun MessageItem(messageText: String, type: MessageType) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (type == MessageType.AI) Alignment.End else Alignment.Start
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
        ) {
            Text(
                text = messageText,
                color = Color.White
            )
        }
    }
}