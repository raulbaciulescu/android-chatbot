package com.university.androidchatbot.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.api.pathtemplate.ValidationException
import com.university.androidchatbot.data.LoginRequest
import com.university.androidchatbot.data.RegisterRequest
import com.university.androidchatbot.data.UserPreferences
import com.university.androidchatbot.repository.AuthRepository
import com.university.androidchatbot.repository.UserPreferencesRepository
import com.university.androidchatbot.screen.TAG
import com.university.androidchatbot.todo.v1.ValidatorUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    var uiState: LoginUiState by mutableStateOf(LoginUiState())

    fun register(firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                validateCredentials(firstName, lastName, email, password)
                Log.v(TAG, "register...");
                uiState = uiState.copy(isAuthenticating = true, authenticationError = null)
                val result =
                    authRepository.register(RegisterRequest(firstName, lastName, email, password))
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

    private fun validateCredentials(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
    ) {
        if (!ValidatorUtil.validateName(firstname))
            throw ValidationException("Please enter your firstname.")

        if (!ValidatorUtil.validateName(lastname))
            throw ValidationException("Please enter your lastname.")

        if (!ValidatorUtil.validateEmail(email))
            throw ValidationException("Please enter a valid email address.")

        if (!ValidatorUtil.validatePassword(password))
            throw ValidationException("The password should have 8 or more characters.")
    }
}