package com.university.androidchatbot.screen

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.university.androidchatbot.R
import com.university.androidchatbot.screen.message


@Composable
fun MessageSection(
    onSendMessage: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            placeholder = { Text("Type a message...") },
            value = message.value,
            onValueChange = {
                message.value = it
            },
            shape = RoundedCornerShape(25.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.send),
                    contentDescription = "send-icon",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .clickable {
                            onSendMessage(message.value)
                            message.value = ""
                        }
                        .size(15.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(13.dp)

        )
    }
}