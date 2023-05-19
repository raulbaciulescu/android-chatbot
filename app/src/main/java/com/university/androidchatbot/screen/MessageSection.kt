package com.university.androidchatbot.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.university.androidchatbot.R
import java.io.File
import com.university.androidchatbot.viewmodel.MessageViewModel
import com.university.androidchatbot.viewmodel.SpeechRecognitionViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageSection(
    onSendMessage: (String) -> Unit,
    messageViewModel: MessageViewModel = hiltViewModel(),
    speechRecognitionViewModel: SpeechRecognitionViewModel = hiltViewModel()
) {
    val isTyping = remember { mutableStateOf(false) }
    val isSpeaking = remember { mutableStateOf(false) }
    var audioFile: File? = null

    val waveRecorder = speechRecognitionViewModel.waveRecorder

    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
//        Row {
//            OutlinedTextField(
//                placeholder = { Text("Type a message...") },
//                value = speechRecognitionViewModel.message,
//                onValueChange = {
//                    speechRecognitionViewModel.message = it
//                    isTyping.value = speechRecognitionViewModel.message != ""
//                },
//                shape = RoundedCornerShape(25.dp),
////            trailingIcon = {
////                if (isTyping.value) {
////                    Icon(
////                        painter = painterResource(id = R.drawable.send),
////                        contentDescription = "send-icon",
////                        tint = MaterialTheme.colors.primary,
////                        modifier = Modifier
////                            .clickable {
////                                onSendMessage(speechRecognitionViewModel.message)
////                                speechRecognitionViewModel.message = ""
////                            }
////                            .size(25.dp))
////                } else {
////                    Icon(
////                        painter = painterResource(id = R.drawable.mic),
////                        contentDescription = "send-icon",
////                        tint = MaterialTheme.colors.primary,
////                        modifier = Modifier
////                            .combinedClickable(
////                                onClick = {
////                                    if (!isSpeaking.value) {
////                                        isSpeaking.value = true
////                                        waveRecorder.startRecording()
////                                    } else {
////                                        waveRecorder.stopRecording()
////                                        speechRecognitionViewModel.transcribe()
////                                    }
////                                },
////                                onLongClick = { println("yes") },
////                            )
////                            .size(25.dp)
////                    )
////                }
////            },
//                modifier = Modifier
//                    .padding(10.dp)
//
//            )
//            FloatingButton(painter = painterResource(id = R.drawable.send))
//        }
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            OutlinedTextField(
                placeholder = { Text("Type a message...") },
                value = speechRecognitionViewModel.message,
                onValueChange = {
                    speechRecognitionViewModel.message = it
                    isTyping.value = speechRecognitionViewModel.message != ""
                },
                modifier = Modifier.width(300.dp),
                shape = RoundedCornerShape(25.dp)
            )
            Spacer(modifier = Modifier.size(12.dp))
            FloatingButton(painter = painterResource(id = R.drawable.send), onClick = {})
        }
    }
}