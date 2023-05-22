package com.university.androidchatbot.screen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.university.androidchatbot.R
import com.university.androidchatbot.components.HorizontalSpace
import com.university.androidchatbot.todo.v1.PressableButton
import java.io.File
import com.university.androidchatbot.viewmodel.MessageViewModel
import com.university.androidchatbot.viewmodel.SpeechRecognitionViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageSection(
    onSendMessage: (String) -> Unit,
    speechRecognitionViewModel: SpeechRecognitionViewModel = hiltViewModel()
) {
    var timeOfTouch: Long = 0
    var timeOfRelease: Long = 0

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
                    if (timeOfRelease - timeOfTouch > 2000)
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
                    onSendMessage(speechRecognitionViewModel.message)
                    speechRecognitionViewModel.message = ""
                }
            )
    }
}