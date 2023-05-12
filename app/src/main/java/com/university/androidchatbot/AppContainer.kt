package com.university.androidchatbot

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.university.androidchatbot.auth.data.AuthRepository
import com.university.androidchatbot.auth.data.remote.AuthDataSource
import com.university.androidchatbot.core.data.UserPreferencesRepository

//val Context.userPreferencesDataStore by preferencesDataStore(
//    name = "user_preferences"
//)

class AppContainer(private val context: Context) {
//    private val authDataSource: AuthDataSource = AuthDataSource()
//
//    val authRepository: AuthRepository by lazy {
//        AuthRepository(authDataSource)
//    }
//
//    val userPreferencesRepository: UserPreferencesRepository by lazy {
//        UserPreferencesRepository(context.userPreferencesDataStore)
//    }
}
