package com.university.androidchatbot.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun AppBar(
    chatTitle: String,
    onNavigationIconClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .statusBarsPadding(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onNavigationIconClick) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Toggle drawer"
            )
        }
        Text(
            text = chatTitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}