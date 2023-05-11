package com.university.androidchatbot.auth.data.remote

import com.university.androidchatbot.core.data.Api
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

class AuthDataSource() {
    interface AuthService {
        @Headers("Content-Type: application/json")
        @POST("/api/auth/login")
        suspend fun login(@Body user: User): TokenHolder
    }

    private val authService: AuthService = Api.retrofit.create(AuthService::class.java)

    suspend fun login(user: User): Result<TokenHolder> {
        return try {
            Result.success(authService.login(user))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

