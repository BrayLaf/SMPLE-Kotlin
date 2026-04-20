package com.example.smple.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    @SerialName("created_at") val createdAt: String = "",
)
