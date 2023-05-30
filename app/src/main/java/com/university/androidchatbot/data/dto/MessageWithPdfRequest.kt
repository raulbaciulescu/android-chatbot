package com.university.androidchatbot.data.dto

import okhttp3.MultipartBody

data class MessageWithPdfRequest(
    val text: MultipartBody.Part,
    var file: MultipartBody.Part
)