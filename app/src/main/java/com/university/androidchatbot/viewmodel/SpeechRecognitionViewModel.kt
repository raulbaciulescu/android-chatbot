package com.university.androidchatbot.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.squti.androidwaverecorder.WaveRecorder
import com.university.androidchatbot.api.SpeechRecognitionDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class SpeechRecognitionViewModel @Inject constructor(
    private val speechRecognitionDataSource: SpeechRecognitionDataSource,
    val application: Application
) : ViewModel()  {
    val waveRecorder = WaveRecorder(application.cacheDir.absolutePath + "/audio.wav")
    var message by mutableStateOf("")

    fun transcribe(): String {
        var result = ""
        val audioFile = File(application.cacheDir.absolutePath + "/audio.wav")
        viewModelScope.launch {
            message = speechRecognitionDataSource.transcribe(audioFile)
            println("&&&&&&&&&& " + result)
        }
        return result
    }
}