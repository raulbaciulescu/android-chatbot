package com.university.androidchatbot.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.university.androidchatbot.R
import com.university.androidchatbot.components.HorizontalSpace
import com.university.androidchatbot.components.VerticalSpace
import com.university.androidchatbot.data.Chat
import com.university.androidchatbot.viewmodel.ChatViewModel

@Composable
fun DrawerScreen(
    chatViewModel: ChatViewModel = hiltViewModel(),
    onNewChatClick: () -> Unit,
    navigate: (chatId: Int) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        DrawerHeader(onNewChatClick = onNewChatClick)
        DrawerBody(
            modifier = Modifier.fillMaxHeight(.85f),
            items = chatViewModel.chats,
            onItemClick = {
                navigate(it.id)
            }
        )
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White.copy(alpha = .3f)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            IconTextButton(
                modifier = Modifier.fillMaxWidth(.9f),
                onClick = onNewChatClick,
                text = "Logout",
                painter = painterResource(R.drawable.ic_logout)
            )
        }
    }
}

@Composable
fun DrawerHeader(onNewChatClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            IconTextButton(
                modifier = Modifier.fillMaxWidth(.9f),
                onClick = onNewChatClick,
                text = "New chat",
                painter = painterResource(R.drawable.ic_plus)
            )
            IconTextButton(
                modifier = Modifier.fillMaxWidth(.9f),
                onClick = onNewChatClick,
                text = "New chat with pdf",
                painter = painterResource(R.drawable.ic_plus)
            )
        }
    }
}

@Composable
fun DrawerBody(
    items: List<Chat>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (Chat) -> Unit
) {
    LazyColumn(modifier) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(vertical = 16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_message),
                    contentDescription = null
                )
                HorizontalSpace(8.dp)
                Text(
                    modifier = Modifier.weight(1f),
                    text = item.title,
                    style = itemTextStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}