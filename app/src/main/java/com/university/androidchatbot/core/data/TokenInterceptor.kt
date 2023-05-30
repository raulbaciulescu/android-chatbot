package com.university.androidchatbot.core.data

import android.util.Log
import com.university.androidchatbot.repository.UserPreferencesRepository
import com.university.androidchatbot.viewmodel.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url
        val token = runBlocking { sessionManager.token.first() }
        if (token.isEmpty()) {
            Log.d(TAG, "Token is null")
            return chain.proceed(original)
        }
        val requestBuilder = original.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .url(originalUrl)
        val request = requestBuilder.build()
        Log.d(TAG, "Authorization bearer added")
        return chain.proceed(request)
    }
}