package com.university.androidchatbot.core.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.university.androidchatbot.MyApplication
import com.university.androidchatbot.core.data.TAG
import com.university.androidchatbot.core.data.TokenInterceptor
import com.university.androidchatbot.core.data.UserPreferences
import com.university.androidchatbot.core.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPreferencesViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val tokenInterceptor: TokenInterceptor
) :
    ViewModel() {
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

//    fun setToken() {
//        tokenInterceptor.
//    }

    fun save(userPreferences: UserPreferences) {
        viewModelScope.launch {
            Log.d(TAG, "saveUserPreferences...");
            userPreferencesRepository.save(userPreferences)
        }
    }
}

