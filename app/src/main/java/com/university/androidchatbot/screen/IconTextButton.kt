package com.university.androidchatbot.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.university.androidchatbot.R
import com.university.androidchatbot.ui.theme.AndroidChatbotTheme

@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    painter: Painter
) {
    OutlinedButton(modifier = modifier, onClick = onClick) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painter,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(text, color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Preview
@Composable
fun PreviewIconTextButton() {
    AndroidChatbotTheme() {
        IconTextButton(text = "New chat", onClick = {}, painter = painterResource(R.drawable.ic_plus))
    }
}