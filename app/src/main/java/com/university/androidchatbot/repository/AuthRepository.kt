package com.university.androidchatbot.repository

import com.university.androidchatbot.api.AuthDataSource
import com.university.androidchatbot.data.AuthenticationResponse
import com.university.androidchatbot.data.dto.LoginRequest
import com.university.androidchatbot.data.dto.RegisterRequest
import com.university.androidchatbot.feature.splash.SessionManager
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val sessionManager: SessionManager
) {
    suspend fun login(loginRequest: LoginRequest): Result<AuthenticationResponse> {
        val result = authDataSource.login(loginRequest)
        if (result.isSuccess) {
            sessionManager.onUserLogin(result.getOrNull()!!.firstName, result.getOrNull()!!.token)
        }
        return result
    }

    suspend fun register(registerRequest: RegisterRequest): Result<AuthenticationResponse> {
        val result = authDataSource.register(registerRequest)
        if (result.isSuccess) {
            sessionManager.onUserLogin(registerRequest.firstName, result.getOrNull()!!.token)
        }
        return result
    }
}
