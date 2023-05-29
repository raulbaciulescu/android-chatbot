package com.university.androidchatbot.data

import okhttp3.MultipartBody
import java.io.File

data class MessageWithPdf(
    val text: MultipartBody.Part,
    var file: MultipartBody.Part
)