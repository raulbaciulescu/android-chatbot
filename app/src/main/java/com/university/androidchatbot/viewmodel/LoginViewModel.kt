package com.university.androidchatbot.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.university.androidchatbot.data.LoginRequest
import com.university.androidchatbot.data.RegisterRequest
import com.university.androidchatbot.screen.TAG
import com.university.androidchatbot.repository.AuthRepository
import com.university.androidchatbot.data.UserPreferences
import com.university.androidchatbot.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val isAuthenticating: Boolean = false,
    val authenticationError: Throwable? = null,
    val authenticationCompleted: Boolean = false,
    val token: String = ""
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    var uiState: LoginUiState by mutableStateOf(LoginUiState())

    fun login(username: String, password: String) {
        viewModelScope.launch {
            Log.v(TAG, "login...");
            uiState = uiState.copy(isAuthenticating = true, authenticationError = null)
            val result = authRepository.login(LoginRequest(username, password))
            uiState = if (result.isSuccess) {
                userPreferencesRepository.save(
                    UserPreferences(
                        username,
                        result.getOrNull()?.token ?: ""
                    )
                )
                uiState.copy(isAuthenticating = false, authenticationCompleted = true)
            } else {
                uiState.copy(
                    isAuthenticating = false,
                    authenticationError = result.exceptionOrNull()
                )
            }
        }
    }

    fun register(firstname: String, lastName: String, email: String, password: String) {
        viewModelScope.launch {
            Log.v(TAG, "register...");
            uiState = uiState.copy(isAuthenticating = true, authenticationError = null)
            val result = authRepository.register(RegisterRequest(firstname, lastName, email, password))
            uiState = if (result.isSuccess) {
                userPreferencesRepository.save(
                    UserPreferences(
                        email,
                        result.getOrNull()?.token ?: ""
                    )
                )
                uiState.copy(isAuthenticating = false, authenticationCompleted = true)
            } else {
                uiState.copy(
                    isAuthenticating = false,
                    authenticationError = result.exceptionOrNull()
                )
            }
        }
    }
}