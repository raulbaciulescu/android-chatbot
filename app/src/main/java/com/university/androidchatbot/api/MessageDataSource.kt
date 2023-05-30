package com.university.androidchatbot.api

import com.university.androidchatbot.data.Chat
import com.university.androidchatbot.data.Message
import com.university.androidchatbot.data.dto.MessageWithPdfRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @Headers("Content-Type: application/json")
    @GET("/messages/{chatId}/{page}")
    suspend fun getMessages(@Path("chatId") chatId: Int, @Path("page") page: Int): List<Message>

    @Multipart
    @POST("/pdf-messages")
    suspend fun saveMessageWithPdf(@Part text: MultipartBody.Part, @Part file: MultipartBody.Part): Message
}

class MessageDataSource @Inject constructor(private val messageApi: MessageApi) {
    suspend fun sendMessage(message: Message): Message {
        return messageApi.saveMessage(message)
    }

    suspend fun sendMessageWithPdf(message: MessageWithPdfRequest): Message {
        return messageApi.saveMessageWithPdf(message.text, message.file)
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

    suspend fun getMessages(chatId: Int, page: Int): Result<List<Message>> {
        return try {
           Result.success(messageApi.getMessages(chatId, page))
        } catch (e: Exception) {
            Result.success(emptyList())
        }
    }
}