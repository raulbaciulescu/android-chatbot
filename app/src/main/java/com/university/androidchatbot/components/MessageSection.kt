package com.university.androidchatbot.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.university.androidchatbot.R
import com.university.androidchatbot.feature.chat.ui.MessageViewModel
import com.university.androidchatbot.utils.Util
import com.university.androidchatbot.viewmodel.SpeechRecognitionViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageSection(
    onSendMessage: (String) -> Unit,
    onSendPdfMessage: (String, String) -> Unit,
    speechRecognitionViewModel: SpeechRecognitionViewModel = hiltViewModel(),
    messageViewModel: MessageViewModel = hiltViewModel(),
) {
    var timeOfTouch: Long = 0
    var timeOfRelease: Long
    val waveRecorder = speechRecognitionViewModel.waveRecorder

    Box(
        Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .imePadding()
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(.87f)
                .align(Alignment.TopStart),
        ) {
            OutlinedTextField(
                placeholder = { Text("Type a message...") },
                value = speechRecognitionViewModel.message,
                onValueChange = {
                    speechRecognitionViewModel.message = it
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(25.dp)
            )
            HorizontalSpace(12.dp)
        }
        if (speechRecognitionViewModel.message == "")
            PressableButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(horizontal = 10.dp),
                painter = painterResource(id = R.drawable.ic_microphone),
                pressButton = { time ->
                    timeOfTouch = time
                    waveRecorder.startRecording()
                },
                releaseButton = { time ->
                    timeOfRelease = time
                    waveRecorder.stopRecording()
                    if (timeOfRelease - timeOfTouch > 1400)
                        speechRecognitionViewModel.transcribe()
                    else {
                        Toast
                            .makeText(
                                speechRecognitionViewModel.application.applicationContext,
                                "Pressed shorter than 1 second",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                }
            )
        else
            FloatingButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(horizontal = 10.dp),
                painter = painterResource(id = R.drawable.ic_send),
                onClick = {
                    if (!messageViewModel.messageState.isLoading) {
                        println("message section " + Util.pdfPath)
                        if (Util.pdfPath != "") {
                            onSendPdfMessage(speechRecognitionViewModel.message, Util.pdfPath)
                            Util.pdfPath = ""
                        } else {
                            onSendMessage(speechRecognitionViewModel.message)
                        }
                        speechRecognitionViewModel.message = ""
                    }
                }
            )
    }
}