package com.university.androidchatbot.todo

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
    suspend fun saveMessage(message: Message): Result<Message> {
        return try {
            Result.success(messageApi.saveMessage(message))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMessages(chatId: Int): List<Message> {
        return messageApi.getMessages(chatId)
    }

    suspend fun getChats(): Result<List<Chat>> {
        return try {
            Result.success(messageApi.getChats())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}