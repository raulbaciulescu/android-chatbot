package com.university.androidchatbot.feature.drawer

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.university.androidchatbot.R
import com.university.androidchatbot.ui.components.HorizontalSpace
import com.university.androidchatbot.ui.components.IconTextButton
import com.university.androidchatbot.utils.UriPathFinder

@Composable
fun DrawerScreen2(
    viewModel: DrawerViewModel,
    onNewChatClick: () -> Unit,
    onNewPdfChatClick: (String) -> Unit,
    onChatClick: (chatId: Int) -> Unit,
    onLogoutSuccessful: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        DrawerHeader(
            onNewChatClick = onNewChatClick,
            onNewChatWithPdfClick = onNewPdfChatClick
        )

        LazyColumn(modifier = Modifier.fillMaxHeight(.85f)) {
            items(uiState.items) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onChatClick(item.id) }
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
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

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
                onClick = {
                    viewModel.logout()
                    onLogoutSuccessful()
                },
                text = "Logout",
                painter = painterResource(R.drawable.ic_logout)
            )
        }
    }
}

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