package com.university.androidchatbot.auth.data

import android.util.Log
import com.university.androidchatbot.auth.data.remote.AuthDataSource
import com.university.androidchatbot.auth.data.remote.TokenHolder
import com.university.androidchatbot.auth.data.remote.LoginRequest
import com.university.androidchatbot.core.data.TAG
import javax.inject.Inject


class AuthRepository @Inject constructor(private val authDataSource: AuthDataSource) {
    init {
        Log.d(TAG, "init")
    }

    fun clearToken() {
        //Api.tokenInterceptor.token = null
    }

    suspend fun login(email: String, password: String): Result<TokenHolder> {
        val loginRequest = LoginRequest(email, password)
        val result = authDataSource.login(loginRequest)
        if (result.isSuccess) {
            //Api.tokenInterceptor.token = result.getOrNull()?.token
        }
        return result
    }
}
