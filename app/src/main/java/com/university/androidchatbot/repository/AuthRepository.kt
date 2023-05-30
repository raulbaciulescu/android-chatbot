package com.university.androidchatbot.repository

import com.university.androidchatbot.api.AuthDataSource
import com.university.androidchatbot.data.TokenHolder
import com.university.androidchatbot.data.LoginRequest
import com.university.androidchatbot.data.RegisterRequest
import com.university.androidchatbot.viewmodel.SessionManager
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val sessionManager: SessionManager
) {
    suspend fun login(loginRequest: LoginRequest): Result<TokenHolder> {
        val result = authDataSource.login(loginRequest)
        if (result.isSuccess) {
            sessionManager.onUserLogin(loginRequest.email, result.getOrNull()!!.token)
        }
        return result
    }

    suspend fun register(registerRequest: RegisterRequest): Result<TokenHolder> {
        val result = authDataSource.register(registerRequest)
        if (result.isSuccess) {
            sessionManager.onUserLogin(registerRequest.email, result.getOrNull()!!.token)
        }
        return result
    }
}
