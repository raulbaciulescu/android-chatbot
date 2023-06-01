package com.university.androidchatbot.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.university.androidchatbot.HOME_ROUTE
import com.university.androidchatbot.LOGIN_ROUTE
import com.university.androidchatbot.data.UserPreferences
import com.university.androidchatbot.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SplashViewModel @Inject constructor(
    private val sessionManager: SessionManager,
) : ViewModel() {
    private val _targetDestination: MutableStateFlow<String?> = MutableStateFlow(null)
    val targetDestination: StateFlow<String?> = _targetDestination

    init {
        viewModelScope.launch {
            val token = sessionManager.token.firstOrNull()
            if (token.isNullOrEmpty()) {
                _targetDestination.value = LOGIN_ROUTE
            } else {
                println("home_route")
                _targetDestination.value = HOME_ROUTE
            }
        }
    }
}

class SessionManager @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) {
    val token: Flow<String> = userPreferencesRepository.userPreferencesStream.map { it.token }

    suspend fun onUserLogin(username: String, token: String) {
        userPreferencesRepository.save(UserPreferences(username, token))
    }

    suspend fun onUserLogOut() {
        userPreferencesRepository.save(UserPreferences())
    }
}