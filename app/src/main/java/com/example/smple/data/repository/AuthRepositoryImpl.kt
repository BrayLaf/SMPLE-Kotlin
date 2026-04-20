package com.example.smple.data.repository

import com.example.smple.domain.model.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email

class AuthRepositoryImpl(private val supabase: SupabaseClient) : AuthRepository {

    override suspend fun signUp(email: String, password: String): Result<User> = runCatching {
        supabase.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
        val user = supabase.auth.currentUserOrNull()
        User(id = user?.id ?: "", email = user?.email ?: email)
    }

    override suspend fun signIn(email: String, password: String): Result<User> = runCatching {
        supabase.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
        val user = supabase.auth.currentUserOrNull()
        User(id = user?.id ?: "", email = user?.email ?: email)
    }

    override suspend fun resetPassword(email: String): Result<Unit> = runCatching {
        supabase.auth.resetPasswordForEmail(email)
    }

    override suspend fun signOut() {
        supabase.auth.signOut()
    }

    override fun getCurrentUser(): User? {
        val user = supabase.auth.currentUserOrNull() ?: return null
        return User(id = user.id, email = user.email ?: "")
    }
}
