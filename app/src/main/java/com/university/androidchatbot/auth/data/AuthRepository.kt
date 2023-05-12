package com.university.androidchatbot.auth.data

import android.util.Log
import com.university.androidchatbot.auth.data.remote.AuthDataSource
import com.university.androidchatbot.auth.data.remote.TokenHolder
import com.university.androidchatbot.auth.data.remote.LoginRequest
import com.university.androidchatbot.auth.data.remote.RegisterRequest
import com.university.androidchatbot.core.data.TAG
import com.university.androidchatbot.core.data.TokenInterceptor
import javax.inject.Inject


class AuthRepository @Inject constructor(private val authDataSource: AuthDataSource, private val tokenInterceptor: TokenInterceptor) {
    fun clearToken() {
        //Api.tokenInterceptor.token = null
    }

    suspend fun login(email: String, password: String): Result<TokenHolder> {
        val loginRequest = LoginRequest(email, password)
        val result = authDataSource.login(loginRequest)
        if (result.isSuccess) {
            tokenInterceptor.token = result.getOrNull()?.token
        }
        return result
    }

    suspend fun register(firstname: String, lastName: String, email: String, password: String): Result<TokenHolder> {
        val registerRequest = RegisterRequest(firstname, lastName, email, password)
        val result = authDataSource.register(registerRequest)
        if (result.isSuccess) {
            tokenInterceptor.token = result.getOrNull()?.token
        }
        return result
    }
}
