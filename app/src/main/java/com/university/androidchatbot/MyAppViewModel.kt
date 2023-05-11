package com.university.androidchatbot

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.university.androidchatbot.core.data.TAG
import com.university.androidchatbot.core.data.UserPreferences
import com.university.androidchatbot.core.data.UserPreferencesRepository
import kotlinx.coroutines.launch

class MyAppViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
) :
    ViewModel() {

    init {
        Log.d(TAG, "init")
    }

    fun logout() {
        viewModelScope.launch {
            userPreferencesRepository.save(UserPreferences())
        }
    }

    fun setToken(token: String) {
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                MyAppViewModel(
                    app.container.userPreferencesRepository,
                )
            }
        }
    }
}


