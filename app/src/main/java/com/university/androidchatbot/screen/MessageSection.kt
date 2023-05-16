package com.university.androidchatbot.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.university.androidchatbot.R
import com.university.androidchatbot.screen.message
import com.university.androidchatbot.todo.AudioRecorderImpl


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageSection(
    onSendMessage: (String) -> Unit,
    audioRecorder: AudioRecorderImpl
) {
    val isTyping = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            placeholder = { Text("Type a message...") },
            value = message.value,
            onValueChange = {
                message.value = it
                isTyping.value = message.value != ""
            },
            shape = RoundedCornerShape(25.dp),
            trailingIcon = {
                if (isTyping.value) {
                    Icon(
                        painter = painterResource(id = R.drawable.send),
                        contentDescription = "send-icon",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .clickable {
                                onSendMessage(message.value)
                                message.value = ""
                            }
                            .size(25.dp))
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.mic),
                        contentDescription = "send-icon",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .combinedClickable(
                                onClick = { println("click") },
                                onLongClick = { println("yes") },
                            )
                            .size(25.dp))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(13.dp)

        )
    }
}

fun startRegister() {

}
