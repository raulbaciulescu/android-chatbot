package com.university.androidchatbot.api

import com.university.androidchatbot.data.dto.LoginRequest
import com.university.androidchatbot.data.dto.RegisterRequest
import com.university.androidchatbot.data.AuthenticationResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Inject


interface AuthApi {
    @Headers("Content-Type: application/json")
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): AuthenticationResponse

    @Headers("Content-Type: application/json")
    @POST("/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): AuthenticationResponse
}

class AuthDataSource @Inject constructor(private val authApi: AuthApi) {
    suspend fun login(loginRequest: LoginRequest): Result<AuthenticationResponse> {
        return try {
            Result.success(authApi.login(loginRequest))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(registerRequest: RegisterRequest): Result<AuthenticationResponse> {
        return try {
            Result.success(authApi.register(registerRequest))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

