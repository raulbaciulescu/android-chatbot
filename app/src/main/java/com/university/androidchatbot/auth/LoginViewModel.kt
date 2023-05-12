package com.university.androidchatbot.auth

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
import com.university.androidchatbot.auth.data.AuthRepository
import com.university.androidchatbot.core.data.UserPreferences
import com.university.androidchatbot.core.data.UserPreferencesRepository
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
            val result = authRepository.login(username, password)
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
            val result = authRepository.register(firstname, lastName, email, password)
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