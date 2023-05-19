package com.university.androidchatbot.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.university.androidchatbot.R
import com.university.androidchatbot.ui.theme.primaryColor

@Composable
fun FloatingButton(
    painter: Painter,
    onClick: () -> Unit,
    initialSize: Dp = 56.dp,
    pressedSize: Dp = 72.dp
) {
    val isPressed = remember { mutableStateOf(false) }

    FloatingActionButton(
        onLongClick = { isPressed.value = true },
        onClick = onClick,
        backgroundColor = primaryColor,
        modifier = Modifier
            .size(if (isPressed.value) pressedSize else initialSize)
    ) {

        Icon(
            painter = painter,
            contentDescription = null,
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .size(25.dp)
        )
    }
}

@Preview
@Composable
fun previewMyButton() {
    FloatingActionButton(
        onClick = { /*TODO*/ },
        backgroundColor = primaryColor
    ) {

        Icon(
            painter = painterResource(id = R.drawable.send),
            contentDescription = "send-icon",
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .clickable {
                }
                .size(25.dp))
    }
}