package com.university.androidchatbot.auth.data.remote

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Inject


interface AuthApi {
    @Headers("Content-Type: application/json")
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): TokenHolder
}

class AuthDataSource @Inject constructor(private val authApi: AuthApi) {
    suspend fun login(loginRequest: LoginRequest): Result<TokenHolder> {
        return try {
            Result.success(authApi.login(loginRequest))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

