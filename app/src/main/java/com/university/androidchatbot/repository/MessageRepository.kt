package com.university.androidchatbot.repository

import com.university.androidchatbot.api.MessageDataSource
import com.university.androidchatbot.data.Chat
import com.university.androidchatbot.data.Message
import com.university.androidchatbot.data.dto.MessageWithPdfRequest
import com.university.androidchatbot.utils.Util
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject


class MessageRepository @Inject constructor(
    private val messageDataSource: MessageDataSource,
) {
    suspend fun getChats(): List<Chat> {
        return messageDataSource.getChats()
    }

    suspend fun sendMessage(message: Message): Result<Message> {
        return messageDataSource.sendMessage(message)
    }

    suspend fun sendMessageWithPdf(message: Message, path: String): Result<Message> {
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

    suspend fun updateChat(chatTitle: String, chatId: Int) {
        messageDataSource.updateChat(Chat(title = chatTitle, id = chatId, filename = ""))
    }
}