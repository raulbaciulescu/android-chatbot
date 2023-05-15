package com.university.androidchatbot

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.university.androidchatbot.core.data.TAG
import com.university.androidchatbot.core.data.TokenInterceptor
import com.university.androidchatbot.core.data.UserPreferences
import com.university.androidchatbot.core.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAppViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val tokenInterceptor: TokenInterceptor,
) : ViewModel() {
    var chatId = mutableStateOf(4)

    fun logout() {
        viewModelScope.launch {
            userPreferencesRepository.save(UserPreferences())
        }
    }

    fun setToken(token: String) {
        tokenInterceptor.token = token
    }
}


