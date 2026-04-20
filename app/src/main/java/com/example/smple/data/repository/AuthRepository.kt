package com.example.smple.data.repository

import com.example.smple.domain.model.User

interface AuthRepository {
    suspend fun signUp(email: String, password: String): Result<User>
    suspend fun signIn(email: String, password: String): Result<User>
    suspend fun resetPassword(email: String): Result<Unit>
    suspend fun signOut()
    fun getCurrentUser(): User?
}
