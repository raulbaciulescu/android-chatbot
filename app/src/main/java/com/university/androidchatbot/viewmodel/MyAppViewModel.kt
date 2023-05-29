package com.university.androidchatbot.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.university.androidchatbot.core.data.TokenInterceptor
import com.university.androidchatbot.data.UserPreferences
import com.university.androidchatbot.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAppViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val tokenInterceptor: TokenInterceptor,
) : ViewModel() {
    var chatId by mutableStateOf(4)

    fun logout() {
        viewModelScope.launch {
            userPreferencesRepository.save(UserPreferences())
        }
    }

    fun setToken(token: String) {
        tokenInterceptor.token = token
    }
}


