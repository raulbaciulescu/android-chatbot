package com.university.androidchatbot.api

import com.university.androidchatbot.data.Chat
import com.university.androidchatbot.data.Message
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject


interface MessageApi {
    @Headers("Content-Type: application/json")
    @GET("/messages/{chatId}")
    suspend fun getMessages(@Path("chatId") chatId: Int): List<Message>

    @Headers("Content-Type: application/json")
    @POST("/messages")
    suspend fun saveMessage(@Body message: Message): Message

    @Headers("Content-Type: application/json")
    @GET("/chats")
    suspend fun getChats(): List<Chat>
}

class MessageDataSource @Inject constructor(private val messageApi: MessageApi) {
    suspend fun sendMessage(message: Message): Message {
        return messageApi.saveMessage(message)
    }

    suspend fun getMessages(chatId: Int): List<Message> {
        return try {
            messageApi.getMessages(chatId)
        } catch (e: Exception) {
            listOf()
        }

    }

    suspend fun getChats(): List<Chat> {
        return messageApi.getChats()
    }
}