package com.university.androidchatbot.feature.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.university.androidchatbot.data.MessageType
import com.university.androidchatbot.ui.components.LoadingMessageItem
import com.university.androidchatbot.ui.components.VerticalSpace
import com.university.androidchatbot.utils.conditional

val BotChatBubbleShape = RoundedCornerShape(0.dp, 20.dp, 20.dp, 20.dp)
val AuthorChatBubbleShape = RoundedCornerShape(20.dp, 0.dp, 20.dp, 20.dp)

@Composable
fun MessagesSection(
    modifier: Modifier = Modifier,
    state: ScreenState,
    messageState: MessageState,
    loadMoreMessages: () -> Unit
) {
    val listState = rememberLazyListState()

    if (state.items.isEmpty())
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Gepeto",
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            VerticalSpace(size = 12.dp)
            Text(
                text = "Write a new message below!",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    else {
        LazyColumn(
            state = listState,
            modifier = modifier.padding(horizontal = 16.dp),
            reverseLayout = true
        ) {
            if (messageState.isLoading) {
                item { LoadingMessageItem() }
            }
            item {
                if (state.isLoading) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            itemsIndexed(state.items) { index, message ->
                // && state.items.size != 2
                if (index >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                    loadMoreMessages()
                }
                MessageItem(
                    messageText = message.text,
                    type = message.type,
                    isLast = index == state.items.lastIndex
                )
            }
        }
    }

    LaunchedEffect(messageState.createMessage) {
        listState.scrollToItem(if (state.items.isNotEmpty()) 0 else 0)
    }
}

@Composable
fun MessageItem(isLast: Boolean, messageText: String, type: MessageType) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .conditional(isLast) {
                padding(top = 16.dp)
            }
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (type == MessageType.USER) MaterialTheme.colorScheme.primary else Color(
                        0xFF616161
                    ),
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