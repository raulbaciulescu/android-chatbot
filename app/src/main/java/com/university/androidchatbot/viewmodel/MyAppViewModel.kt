package com.university.androidchatbot.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.university.androidchatbot.feature.splash.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAppViewModel @Inject constructor(
    val sessionManager: SessionManager
) : ViewModel() {
    //var chatId by mutableStateOf(0)

    fun logout() {
        viewModelScope.launch {
            sessionManager.onUserLogOut()
        }
    }
}


