package com.example.smple.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.smple.SmpleApplication
import com.example.smple.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepo = (application as SmpleApplication).authRepository

    private val _currentUser = MutableStateFlow(authRepo.getCurrentUser())
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun refresh() {
        _currentUser.value = authRepo.getCurrentUser()
    }

    fun signOut(onDone: () -> Unit) {
        viewModelScope.launch {
            authRepo.signOut()
            onDone()
        }
    }

    fun deleteAccount(onDone: () -> Unit) {
        // Supabase requires an edge function or admin API to delete an account from client-side.
        // For now, sign out and navigate away; deletion can be added via a Supabase edge function.
        viewModelScope.launch {
            authRepo.signOut()
            onDone()
        }
    }
}
