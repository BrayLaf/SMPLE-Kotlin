package com.example.smple.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.smple.SmpleApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = (application as SmpleApplication).authRepository

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun signIn(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repo.signIn(email, password)
                .onSuccess { _uiState.value = UiState.Idle; onSuccess() }
                .onFailure { _uiState.value = UiState.Error(it.message ?: "Sign in failed") }
        }
    }

    fun signUp(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repo.signUp(email, password)
                .onSuccess { _uiState.value = UiState.Idle; onSuccess() }
                .onFailure { _uiState.value = UiState.Error(it.message ?: "Sign up failed") }
        }
    }

    fun resetPassword(email: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repo.resetPassword(email)
                .onSuccess { _uiState.value = UiState.Idle; onSuccess() }
                .onFailure { _uiState.value = UiState.Error(it.message ?: "Reset failed") }
        }
    }

    fun clearError() { _uiState.value = UiState.Idle }
}
