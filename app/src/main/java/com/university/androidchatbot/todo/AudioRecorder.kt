package com.university.androidchatbot.todo

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}