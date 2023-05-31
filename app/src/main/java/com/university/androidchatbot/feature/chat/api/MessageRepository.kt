package com.university.androidchatbot.feature.chat.api

import android.app.Application
import com.google.gson.Gson
import com.university.androidchatbot.api.MessageDataSource
import com.university.androidchatbot.data.Chat
import com.university.androidchatbot.data.Message
import com.university.androidchatbot.data.dto.MessageWithPdfRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject


class MessageRepository @Inject constructor(
    private val messageDataSource: MessageDataSource,
    private val gson: Gson,
    private val application: Application
    ) {
    suspend fun getMessages(chatId: Int): List<Message> {
        return messageDataSource.getMessages(chatId)
    }

    suspend fun getChats(): List<Chat> {
        return messageDataSource.getChats()
    }

    suspend fun sendMessage(message: Message): Message {
        return messageDataSource.sendMessage(message)
    }

    suspend fun sendMessageWithPdf(message: Message, path: String): Message {
        return messageDataSource.sendMessageWithPdf(
            MessageWithPdfRequest(
                text = MultipartBody.Part
                    .createFormData(
                        "text",
                        message.text
                    ),
                file = MultipartBody.Part
                    .createFormData(
                        name = "file",
                        filename = File(path).name,
                        body = File(path).asRequestBody()
                    )
            )
        )
    }

    suspend fun getMessages(chatId: Int, page: Int): Result<List<Message>> {
        return messageDataSource.getMessages(chatId, page)
    }

    suspend fun deleteChat(chatId: Int) {
        messageDataSource.deleteChat(chatId)
    }
}