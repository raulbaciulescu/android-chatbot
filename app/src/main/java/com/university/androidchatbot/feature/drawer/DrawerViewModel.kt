package com.university.androidchatbot.feature.drawer

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.university.androidchatbot.data.Chat
import com.university.androidchatbot.feature.splash.SessionManager
import com.university.androidchatbot.repository.MessageRepository
import com.university.androidchatbot.utils.updateValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DrawerUiState(
    val isLoading: Boolean = false,
    val items: List<Chat> = mutableStateListOf(),
    val error: String? = null
)

@HiltViewModel
class DrawerViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState: MutableStateFlow<DrawerUiState> = MutableStateFlow(DrawerUiState())
    val uiState: StateFlow<DrawerUiState>
        get() = _uiState.asStateFlow()

    init {
        refreshChats()
    }

    fun refreshChats() {
        _uiState.updateValue { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = messageRepository.getChats()
            _uiState.updateValue { it.copy(isLoading = false, items = result) }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.onUserLogOut()
        }
    }

    fun getLoggedFirstName(): String {
        var firstName = ""
        viewModelScope.launch {
            firstName = sessionManager.firstName.first()
        }
        return firstName
    }
}