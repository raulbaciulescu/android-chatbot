package com.university.androidchatbot.auth.data

import android.util.Log
import com.university.androidchatbot.auth.TAG
import com.university.androidchatbot.auth.data.remote.AuthDataSource
import com.university.androidchatbot.auth.data.remote.TokenHolder
import com.university.androidchatbot.auth.data.remote.User
import com.university.androidchatbot.core.data.Api


class AuthRepository(private val authDataSource: AuthDataSource) {
    init {
        Log.d(TAG, "init")
    }

    fun clearToken() {
        Api.tokenInterceptor.token = null
    }

    suspend fun login(username: String, password: String): Result<TokenHolder> {
        val user = User(username, password)
        val result = authDataSource.login(user)
        if (result.isSuccess) {
            Api.tokenInterceptor.token = result.getOrNull()?.token
        }
        return result
    }
}
