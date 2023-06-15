package com.university.androidchatbot.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.university.androidchatbot.R
import com.university.androidchatbot.ui.theme.AndroidChatbotTheme
import com.university.androidchatbot.ui.theme.PrimaryPurple

@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    painter: Painter
) {
    OutlinedButton(modifier = modifier, onClick = onClick) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painter,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
            HorizontalSpace(8.dp)
            Text(text, color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Composable
fun PressableButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    initialSize: Dp = 56.dp,
    pressedSize: Dp = 72.dp,
    pressButton: (Long) -> Unit,
    releaseButton: (Long) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var isBegin by remember { mutableStateOf(true) }
    var initial by rememberSaveable { mutableStateOf(false) }
    val buttonSize by animateDpAsState(targetValue = if (isPressed) pressedSize else initialSize)

    FloatingActionButton(
        modifier = modifier.size(buttonSize),
        shape = CircleShape,
        onClick = { },
        interactionSource = interactionSource,
        containerColor = PrimaryPurple
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            painter = painter,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }

    LaunchedEffect(isPressed) {
        if (initial) {
            isBegin = if (isBegin) {
                pressButton(System.currentTimeMillis())
                false
            } else {
                releaseButton(System.currentTimeMillis())
                true
            }
        } else
            initial = true
    }
}

@Composable
fun FloatingButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        shape = CircleShape,
        onClick = onClick,
        containerColor = PrimaryPurple,
        modifier = modifier.size(56.dp),
    ) {

        Icon(
            painter = painter,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(25.dp)
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

@Preview
@Composable
fun PreviewPressableButton() {
    PressableButton(
        painter = painterResource(R.drawable.ic_microphone),
        pressButton = {},
        releaseButton = {}
    )
}

@Preview
@Composable
fun PreviewIconTextButton() {
    AndroidChatbotTheme() {
        IconTextButton(
            text = "New chat",
            onClick = {},
            painter = painterResource(R.drawable.ic_plus)
        )
    }
}