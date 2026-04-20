package com.example.smple.data.remote.api

import com.example.smple.data.remote.dto.UserDto

interface AuthApi {
    suspend fun signUp(email: String, password: String): UserDto
    suspend fun signIn(email: String, password: String): UserDto
    suspend fun resetPassword(email: String)
    suspend fun signOut()
    fun getCurrentUser(): UserDto?
}
