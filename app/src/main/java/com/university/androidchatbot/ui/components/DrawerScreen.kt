package com.university.androidchatbot.ui.components

import android.app.Application
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.university.androidchatbot.R
import com.university.androidchatbot.data.Chat
import com.university.androidchatbot.feature.chat.ChatViewModel
import com.university.androidchatbot.utils.UriPathFinder
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext

//@Composable
//fun DrawerScreen(
//    chatViewModel: ChatViewModel,
//    onNewChatClick: () -> Unit,
//    onNewChatWithPdfClick: (String) -> Unit,
//    onLogoutClick: () -> Unit,
//    navigate: (chatId: Int) -> Unit,
//    application: Application
//) {
//    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
//        DrawerHeader(
//            onNewChatClick = onNewChatClick,
//            onNewChatWithPdfClick = onNewChatWithPdfClick,
//            application = application
//        )
//        DrawerBody(
//            modifier = Modifier.fillMaxHeight(.85f),
//            viewModel = chatViewModel,
//            onItemClick = {
//                navigate(it.id)
//            }
//        )
//        Divider(
//            modifier = Modifier.fillMaxWidth(),
//            color = Color.White.copy(alpha = .3f)
//        )
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 30.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            IconTextButton(
//                modifier = Modifier.fillMaxWidth(.9f),
//                onClick = onLogoutClick,
//                text = "Logout",
//                painter = painterResource(R.drawable.ic_logout)
//            )
//        }
//    }
//}

@Composable
fun DrawerHeader(
    onNewChatClick: () -> Unit,
    onNewChatWithPdfClick: (String) -> Unit,
) {
    var showFilePicker by remember { mutableStateOf(false) }
    val uriPathFinder = UriPathFinder()
    val context = LocalContext.current

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
                onClick = {
                    showFilePicker = true
                },
                text = "New chat with pdf",
                painter = painterResource(R.drawable.ic_plus)
            )
            FilePicker(show = showFilePicker, fileExtensions = listOf("pdf")) { path ->
                showFilePicker = false
                val myPath = uriPathFinder.handleUri(context, Uri.parse(path!!.path))
                onNewChatWithPdfClick(myPath!!)
            }
        }
    }
}

@Composable
fun DrawerBody(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel(),
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (Chat) -> Unit,
) {
    val uiState by viewModel.chatUiState.collectAsState()

    LazyColumn(modifier) {
        items(uiState.items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(if (item.filename == null) R.drawable.ic_message else R.drawable.ic_pdf),
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