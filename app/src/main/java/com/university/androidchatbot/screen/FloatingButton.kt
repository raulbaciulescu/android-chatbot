package com.university.androidchatbot.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.university.androidchatbot.R
import com.university.androidchatbot.ui.theme.AndroidChatbotTheme
import com.university.androidchatbot.ui.theme.primaryColor

@Composable
fun FloatingButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    onClick: () -> Unit,
    initialSize: Dp = 56.dp,
    pressedSize: Dp = 72.dp,
) {
    val isPressed = remember { mutableStateOf(false) }

    FloatingActionButton(
        shape = CircleShape,
        onClick = onClick,
        containerColor = primaryColor,
        modifier = modifier
            .size(if (isPressed.value) pressedSize else initialSize),
    ) {

        Icon(
            painter = painter,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .size(25.dp)
        )
    }
}

@Preview
@Composable
fun PreviewFloatingButton() {
    AndroidChatbotTheme() {
        FloatingButton(onClick = {}, painter = painterResource(R.drawable.ic_send))
    }
}