package com.university.androidchatbot.feature.authentication.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.api.pathtemplate.ValidationException
import com.university.androidchatbot.data.dto.LoginRequest
import com.university.androidchatbot.repository.AuthRepository
import com.university.androidchatbot.data.UserPreferences
import com.university.androidchatbot.repository.UserPreferencesRepository
import com.university.androidchatbot.utils.ValidatorUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val isAuthenticating: Boolean = false,
    val authenticationError: String? = null,
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
            try {
                validateCredentials(username, password)
                Log.v(TAG, "login...");
                uiState = uiState.copy(isAuthenticating = true, authenticationError = null)
                val result = authRepository.login(LoginRequest(username, password))
                uiState = if (result.isSuccess) {
                    uiState.copy(isAuthenticating = false, authenticationCompleted = true)
                } else {
                    uiState.copy(
                        isAuthenticating = false,
                        authenticationError = result.exceptionOrNull()?.message
                    )
                }
            } catch (ex: ValidationException) {
                uiState = uiState.copy(
                    isAuthenticating = false,
                    authenticationError = ex.message
                )
            }
        }
    }

    private fun validateCredentials(email: String, password: String) {
        if (!ValidatorUtil.validateEmail(email))
            throw ValidationException("Please enter a valid email address.")

        if (!ValidatorUtil.validatePassword(password))
            throw ValidationException("The password should have 8 or more characters.")
    }
}