package com.university.androidchatbot.todo

import com.university.androidchatbot.auth.data.remote.AuthApi
import javax.inject.Inject


class MessageRepository @Inject constructor(private val messageDataSource: MessageDataSource) {
    suspend fun getMessages(chatId: Int): List<Message> {
        return messageDataSource.getMessages(chatId)
    }
}