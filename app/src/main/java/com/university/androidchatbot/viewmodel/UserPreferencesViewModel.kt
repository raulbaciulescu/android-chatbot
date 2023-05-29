package com.university.androidchatbot.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.university.androidchatbot.core.data.TAG
import com.university.androidchatbot.core.data.TokenInterceptor
import com.university.androidchatbot.data.UserPreferences
import com.university.androidchatbot.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPreferencesViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    var uiState: UserPreferences by mutableStateOf(UserPreferences())
        private set

    init {
        Log.d(TAG, "init")
        load()
    }

    fun load() {
        viewModelScope.launch {
            userPreferencesRepository.userPreferencesStream.collect { userPreferences ->
                uiState = userPreferences
            }
        }
    }
}

