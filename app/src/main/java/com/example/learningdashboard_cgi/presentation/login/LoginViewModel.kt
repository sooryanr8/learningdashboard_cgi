package com.example.learningdashboard_cgi.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningdashboard_cgi.data.AuthRepository
import kotlinx.coroutines.launch

sealed class LoginUiState
{
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    var uiState by mutableStateOf<LoginUiState>(LoginUiState.Idle)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            uiState = LoginUiState.Loading

            val success = repository.login(email, password)

            if (success) {
                uiState = LoginUiState.Success
            } else {
                uiState = LoginUiState.Error("Invalid email or password")
            }
        }
    }
}