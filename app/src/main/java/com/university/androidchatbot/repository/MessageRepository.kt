package com.university.androidchatbot.repository

import com.university.androidchatbot.api.MessageDataSource
import com.university.androidchatbot.data.Chat
import com.university.androidchatbot.data.Message
import javax.inject.Inject


class MessageRepository @Inject constructor(private val messageDataSource: MessageDataSource) {
    suspend fun getMessages(chatId: Int): List<Message> {
        return messageDataSource.getMessages(chatId)
    }

    suspend fun getChats(): List<Chat> {
        return messageDataSource.getChats()
    }

    suspend fun sendMessage(message: Message): Message {
        return messageDataSource.sendMessage(message)
    }
}