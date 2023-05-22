package com.university.androidchatbot.api

import com.university.androidchatbot.data.SpeechToTextResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File
import javax.inject.Inject


interface SpeechRecognitionApi {
    @POST("/transcribe")
    @Multipart
    suspend fun transcribe(@Part file: MultipartBody.Part): SpeechToTextResponse

    @GET("/hello")
    @Headers("Content-Type: application/json")
    suspend fun hello(): String
}

class SpeechRecognitionDataSource @Inject constructor(private val speechRecognitionApi: SpeechRecognitionApi) {
    suspend fun transcribe(file: File): String {
        val filePart: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, file.asRequestBody())
        return speechRecognitionApi.transcribe(filePart).text
    }
}